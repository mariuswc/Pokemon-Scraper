package org.example.service

import io.github.oshai.kotlinlogging.KLogger
import it.skrape.selects.Doc
import org.example.client.PokemonClient
import org.example.data.PokemonResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import it.skrape.selects.DocElement
import it.skrape.selects.text
import org.example.filter.pokemonFilter
import org.springframework.stereotype.Service


@Service
class PokemonService(
    private val pokemonClient: PokemonClient,
    private val pokemonFilter: pokemonFilter

) {
    fun getPokemon(pokeset: String): PokemonResponse {
        val doc = pokemonClient.getSiteHtml()

        pokemonFilter.filteringPokemonSets(doc, pokeset)

        val name = pokemonFilter.findPokemonName(doc)
        val price = pokemonFilter.findPokemonPrice(doc)
        val stock = pokemonFilter.findPokemonStockStatus(doc)
        return PokemonResponse(name, price, stock)
    }















}






