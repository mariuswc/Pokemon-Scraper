package org.example.service

import client.PokemonClient
import client.pokemonClient
import it.skrape.core.document
import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Method
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import it.skrape.selects.eachLink
import it.skrape.selects.html5.a
import it.skrape.selects.html5.p
import kotlinx.coroutines.withTimeout
import org.example.data.PokemonResponse
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.springframework.boot.Banner


@Service
class PokemonService(
    private val pokemonClient: PokemonClient,
) {



    fun doSomethingWithHTML(): PokemonResponse {
        val scrapedSite = pokemonClient.getPokemonSet()
        val name = scrapedSite
        val price = scrapedSite.length
        val stockStatus = scrapedSite.toBoolean()
        return PokemonResponse(name, price, stockStatus)

    }
}
