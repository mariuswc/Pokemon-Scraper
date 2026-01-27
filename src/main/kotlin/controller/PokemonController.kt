package org.example.controller

import it.skrape.selects.Doc
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.example.service.PokemonService

@RestController
@RequestMapping("api/pokemon")
class PokemonController(
    val pokemonService: PokemonService,
){

    @GetMapping()
    fun getPokemon(): PokemonService.Html {
        return pokemonService.getPokemonSet()
    }

}
