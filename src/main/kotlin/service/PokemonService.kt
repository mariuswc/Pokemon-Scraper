package org.example.service

import io.github.oshai.kotlinlogging.KLogger
import it.skrape.selects.Doc
import org.example.client.PokemonClient
import org.example.data.PokemonResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service


@Service
class PokemonService(
    private val pokemonClient: PokemonClient,
    private val logger: KLogger = KotlinLogging.logger {}

) {
    fun getPokemon(): PokemonResponse {
        val doc = pokemonClient.getSiteHtml()
        val name = findPokemonName(doc)
        val price = findPokemonPrice(doc)
        val stock = findPokemonStockStatus(doc)
        return PokemonResponse(name, price, stock)
    }

    fun findPokemonName(doc: Doc): String {
        try {
            return doc.findAll("h1").firstOrNull()?.text ?: ""
        } catch (e: Exception) {
            throw IllegalArgumentException("Unable to find Pokemon name", e)
        }
    }


        fun findPokemonPrice(doc: Doc): String {
            try {
                return doc.findFirst("price_display").toString()
                    ?: "Unknown price"
            } catch (e: Exception) {
                throw IllegalArgumentException("Unable to find price", e)
            }
        }


        fun findPokemonStockStatus(doc: Doc): Boolean { //can also filter on product__meta-numbers
            try {
                val buyButton = doc.findFirst("button.product__buy-button")

                val inStock = when {
                    buyButton == null -> false // not in stock
                    buyButton.hasAttribute("disabled") -> false //not in stock
                    buyButton.text.contains("utsolgt", ignoreCase = true) -> false //not in stock
                    else -> true //returns inStock
                }
                return inStock
            } catch (e: Exception) {
                throw IllegalArgumentException("Unable to get stock status", e)
            }
        }
    }





