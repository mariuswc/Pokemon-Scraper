package org.example.filter

import it.skrape.selects.Doc
import org.example.service.PokemonService

class pokemonFilter(
    private val pokemonService: PokemonService
)

{


    fun filteringPokemonSets(doc: Doc, pokeset: String){
        pokeset.toString()

    }



    fun findPokemonName(doc: Doc): String? {
        try {
            return doc
                .findFirst("h1.product__title[itemprop=name]")
                ?.text


        } catch (e: Exception) {
            throw IllegalArgumentException("Unable to find Pokemon name", e)
        }
    }


    fun findPokemonPrice(doc: Doc): String? {
        try {
            return doc
                .findFirst("[itemprop=price]")
                .attribute("content")


        } catch (e: Exception) {
            throw IllegalArgumentException("Unable to find price", e)
        }
    }


    fun findPokemonStockStatus(doc: Doc): Boolean {
        return try {
            doc.findFirst("[itemprop=availability]")
                ?.attribute("href")
                ?.endsWith("InStock") == true
        } catch (e: Exception) {
            throw IllegalArgumentException("Unable to get stock status", e)
        }
    }








}