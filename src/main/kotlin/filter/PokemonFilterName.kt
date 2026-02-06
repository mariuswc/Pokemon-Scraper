package org.example.filter

import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import org.springframework.context.annotation.Configuration

@Configuration
class PokemonFilter {

    fun findProduct(doc: Doc, requestedName: String, productId: String? = null): DocElement {
        val normalizedRequested = requestedName.normalize()

        // 0) If productId is provided, prefer exact match on id.
        if (!productId.isNullOrBlank()) {
            val normalizedId = productId.trim()
            val byDataId = runCatching { doc.findFirst("[data-product-id='$normalizedId']") }.getOrNull()
            if (byDataId != null) return byDataId

            val byBuyButtonId = runCatching { doc.findFirst("#buy-button-$normalizedId") }.getOrNull()
            if (byBuyButtonId != null) {
                // Return the closest product container that contains this button.
                val container = runCatching {
                    // Common wrappers
                    byBuyButtonId.parents.firstOrNull { p ->
                        runCatching { p.hasClass("productlist__product") }.getOrDefault(false) ||
                            runCatching { p.hasClass("product") }.getOrDefault(false)
                    }
                }.getOrNull()
                if (container != null) return container
            }
        }

        // 1) If we're already on a product page, treat the page root as the product container.
        val isProductPage = runCatching {
            doc.findFirst("h1.product__title, h1[itemprop=name]")
        }.getOrNull() != null
        if (isProductPage) {
            // Use a stable root element for subsequent findFirst() calls.
            return runCatching { doc.findFirst("body") }.getOrNull()
                ?: runCatching { doc.findFirst("html") }.getOrNull()
                ?: throw IllegalArgumentException("Product page root not found")
        }

        // 2) Otherwise: product listing page. Try a few likely selectors for product cards.
        val candidatesSelectors = listOf(
            ".product",
            ".productlist__product",
            ".productlist__product__item",
            "[data-product-id]"
        )

        val products: List<DocElement> = candidatesSelectors
            .asSequence()
            .flatMap { selector ->
                // skrape throws ElementNotFoundException when selector doesn't exist.
                runCatching { doc.findAll(selector) }
                    .getOrDefault(emptyList())
                    .asSequence()
            }
            .distinctBy { el ->
                // Best-effort dedupe
                runCatching { el.attribute("data-product-id") }.getOrNull() ?: el.hashCode().toString()
            }
            .toList()

        return products.firstOrNull { product ->
            val productName = findPokemonNameOrNull(product)?.normalize() ?: return@firstOrNull false

            // Be tolerant: sometimes input contains extra characters (e.g. trailing ']' from client).
            // Match both directions and also token-based overlap.
            if (productName.contains(normalizedRequested) || normalizedRequested.contains(productName)) return@firstOrNull true

            val reqTokens = normalizedRequested.split(' ').filter { it.length >= 3 }.toSet()
            val nameTokens = productName.split(' ').filter { it.length >= 3 }.toSet()
            val overlap = reqTokens.intersect(nameTokens).size
            overlap >= 2
        } ?: throw IllegalArgumentException("Product not found: $requestedName")
    }

    fun findPokemonName(product: DocElement): String =
        findPokemonNameOrNull(product) ?: throw IllegalArgumentException("Pokemon name not found")

    private fun findPokemonNameOrNull(product: DocElement): String? {
        // Try common name locations across listing & product page.
        return runCatching {
            product.findFirst("h1.product__title")
        }.getOrNull()?.text
            ?: runCatching {
                product.findFirst("h1[itemprop=name]")
            }.getOrNull()?.text
            ?: runCatching {
                product.findFirst("h3.productlist__product__headline")
            }.getOrNull()?.text
            ?: runCatching {
                product.findFirst("h3[itemprop=name]")
            }.getOrNull()?.text
            ?: runCatching {
                product.findFirst("[itemprop=name]")
            }.getOrNull()?.text
            ?: runCatching {
                product.findFirst(".productlist__product__name")
            }.getOrNull()?.text
            ?: runCatching {
                product.findFirst(".product__title")
            }.getOrNull()?.text
    }

    fun findPokemonPrice(product: DocElement): Int =
        runCatching {
            product.findFirst("[itemprop=price]")
        }.getOrNull()
            ?.let { priceEl ->
                runCatching { priceEl.attribute("content").toInt() }.getOrNull()
            }
            ?: runCatching {
                // Fallback: try scraping visible price text like "1 299,-" etc.
                val priceEl = runCatching {
                    product.findFirst(".productlist__product__price, .product__price")
                }.getOrNull() ?: return@runCatching null

                val priceText = priceEl.text
                priceText
                    .replace(Regex("[^0-9]"), "")
                    .takeIf { it.isNotBlank() }
                    ?.toInt()
            }.getOrNull()
            ?: throw IllegalArgumentException("Pokemon price not found")

    fun findPokemonStockStatus(product: DocElement): Boolean {
        // We consider it in stock if there's a buy/add-to-cart style button that is enabled.
        // If the only action is "Les mer" (read more), we treat it as not available.

        val action = runCatching {
            product.findFirst(
                ".product__buy-button, .productlist__product__button, button[id^=buy-button-], [id^=buy-button-]"
            )
        }.getOrNull() ?: return false

        val rawText = runCatching { action.text }.getOrNull().orEmpty()
        val text = rawText.replace(Regex("\\s+"), " ").trim()

        val disabled = runCatching { action.hasAttribute("disabled") }.getOrDefault(false) ||
            runCatching { action.attribute("aria-disabled") }.getOrNull().equals("true", ignoreCase = true)

        if (disabled) return false

        // Rule: "Les mer" is NOT a buy action -> treat as out of stock/unavailable.
        if (text.equals("les mer", ignoreCase = true)) return false

        // Common positive indicators
        if (text.contains("legg", ignoreCase = true) ||
            text.contains("kurv", ignoreCase = true) ||
            text.contains("kjøp", ignoreCase = true) ||
            text.contains("buy", ignoreCase = true) ||
            text.contains("add", ignoreCase = true)
        ) return true

        // If it's not explicitly "Les mer" and not disabled, assume available.
        return true
    }


    private fun String.normalize(): String =
        lowercase()
            .replace("pokémon", "pokemon")
            .replace("’", "'")
            .replace("-", " ")
            .replace(Regex("\\s+"), " ")
            .replace(Regex("[^a-z0-9 ]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
}
