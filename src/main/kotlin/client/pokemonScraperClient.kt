//package org.example.client
//
//import it.skrape.core.htmlDocument
//import it.skrape.fetcher.AsyncFetcher
//import it.skrape.fetcher.response
//import it.skrape.fetcher.skrape
//
//class pokemonScraperClient {
//
//suspend fun getAllPokemon(): Map<String, String> = skrape(AsyncFetcher) {
//    request {
//        url = ("https://pokestore.no/")
//
//
//    }
//    response {
//        htmlDocument { eachLink }
//    }
//}
//
//}
//
