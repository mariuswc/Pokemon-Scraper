package org.example.filter

import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import org.springframework.context.annotation.Configuration

@Configuration
class PokemonFilter {

    fun findProduct(doc: Doc, requestedName: String): DocElement {
        return doc.findAll(".product")
            .firstOrNull { product ->
                val productName = product
                    .findFirst("[itemprop=name]")
                    ?.text
                    ?.normalize()

                productName?.contains(requestedName.normalize()) == true
            }
            ?: throw IllegalArgumentException("Product not found: $requestedName")
    }

    fun findPokemonName(product: DocElement): String =
        product.findFirst("[itemprop=name]")?.text
            ?: throw IllegalArgumentException("Pokemon name not found")

    fun findPokemonPrice(product: DocElement): Int =
        product.findFirst("[itemprop=price]")
            ?.attribute("content")
            ?.toInt()
            ?: throw IllegalArgumentException("Pokemon price not found")

    fun findPokemonStockStatus(product: DocElement): Boolean {
        val action = product.findFirst(
            ".product__buy-button, .productlist__product__button"
        )

        return when {
            action == null -> false
            action.hasAttribute("disabled") -> false
            action.text.contains("les mer", true) -> false
            action.text.contains("utsolgt", true) -> false
            else -> true
        }
    }


    private fun String.normalize(): String =
        lowercase()
            .replace("pokémon", "pokemon")
            .replace("-", " ")
            .replace(Regex("\\s+"), " ")
            .trim()
}
