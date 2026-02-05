package org.example.controller

import org.example.service.PokemonService
import org.example.data.PokemonRequest
import org.example.data.PokemonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody



@RestController
@RequestMapping("/api/pokemon")
class PokemonController(
    val pokemonService: PokemonService,
){
    @PostMapping()
    fun getPokemon(
        @RequestBody(required = true) pokeSet: PokemonRequest
    ): PokemonResponse  {
        return pokemonService.getPokemon(pokeSet)
    }

}
