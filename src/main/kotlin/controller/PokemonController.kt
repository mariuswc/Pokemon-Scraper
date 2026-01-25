package org.example.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.example.service.PokemonService
import org.example.persistance.PokemonDB
import org.example.data.PokemonResponse

@RestController
@RequestMapping("api/pokemon")
class PokemonController(
    val pokemonService: PokemonService,
    val pokemonDB: PokemonDB,
){

    @GetMapping("/{pokemonSet}")
    fun getPokemon(@PathVariable pokemonSet: String): PokemonResponse {
        return pokemonService.getPokemonSet(pokemonSet)
    }

}
