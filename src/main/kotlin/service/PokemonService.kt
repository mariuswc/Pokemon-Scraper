package org.example.service

import it.skrape.core.document
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import it.skrape.selects.eachLink
import it.skrape.selects.html5.a
import it.skrape.selects.html5.p
import org.springframework.stereotype.Service


@Service
class PokemonService() {

    data class Html(
        val htmlResponse: String,
    )

    fun getPokemonSet(): Html {
        val extracted = skrape(HttpFetcher) {
            request { url { "https://poke-shop.no/" } }
            response { document.findAll("") }

        }
        return Html(htmlResponse = extracted.toString())
    }
}
