package org.example.service

import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import org.springframework.stereotype.Service
import org.example.data.PokemonResponse
import org.example.persistance.PokemonDB

@Service
class PokemonService(
    val pokemonDB: PokemonDB,
) {


    fun getPokemonSet(pokemonSet: String): PokemonResponse {
        val name =
    }


}
