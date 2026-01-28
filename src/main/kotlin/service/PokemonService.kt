package org.example.service

import it.skrape.core.document
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
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.springframework.boot.Banner


@Service
class PokemonService() {

    //Not implemented yet
//    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(javaClass)

    data class Html(
        val htmlResponse: Doc
    )

    fun getPokemonSet(): Html {
        val extracted = skrape(BrowserFetcher) {
            request {
                url = "https://poke-shop.no/"
                method = Method.GET
                sslRelaxed = true
            }
            response { document }
        }
        return extracted
    }
}
