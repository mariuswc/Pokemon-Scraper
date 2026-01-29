package org.example.service


import it.skrape.selects.text
import org.example.client.PokemonClient
import org.example.data.PokemonResponse
import org.springframework.stereotype.Service


@Service
class PokemonService(
    private val pokemonClient: PokemonClient,
) {

    fun doSomethingWithHTML(): PokemonResponse {
        val scrapedRawData = pokemonClient.getPokemonSet()
        val name = scrapedRawData?.findByIndex(1)?.text
        val price = scrapedRawData?.findByIndex(3)?.text
        val stockStatus = scrapedRawData?.findByIndex(2)?.text
        return PokemonResponse(name, price, stockStatus)

    }
}
