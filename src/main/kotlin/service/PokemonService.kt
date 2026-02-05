package org.example.service

import org.example.client.PokemonClient
import org.example.data.PokemonRequest
import org.example.data.PokemonResponse
import org.example.filter.PokemonFilter
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonClient: PokemonClient,
    private val pokemonFilter: PokemonFilter
) {
    fun getPokemon(pokeset: PokemonRequest): PokemonResponse {
        val doc = pokemonClient.getSiteHtml()

        val product = pokemonFilter.findProduct(doc, pokeset.name)

        val name = pokemonFilter.findPokemonName(product)
        val price = pokemonFilter.findPokemonPrice(product)
        val stock = pokemonFilter.findPokemonStockStatus(product)

        return PokemonResponse(name, price, stock)
    }
}
