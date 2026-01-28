package client

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.Method
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape

class PokemonClient {
    fun getPokemonSet(): String {
        val extracted = skrape(BrowserFetcher) {
            request {
                url = "https://poke-shop.no/"
                method = Method.GET
                sslRelaxed = true
            }
            response { htmlDocument { document.toString() } }
        }
        return extracted
    }
}
