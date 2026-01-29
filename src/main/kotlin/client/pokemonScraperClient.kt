package org.example.client

import it.skrape.core.document
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.Method
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import org.springframework.stereotype.Component

@Component
class PokemonClient {
    fun getPokemonSet(): Doc {
        val extracted = skrape(BrowserFetcher) {
            request {
                url = "https://poke-shop.no/"
                method = Method.GET
                sslRelaxed = true
            }
            response {document}

        }
        return extracted
    }
}
