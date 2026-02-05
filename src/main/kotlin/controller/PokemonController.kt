package org.example.controller


import org.example.data.PokemonRequest
import org.example.data.PokemonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.example.service.PokemonService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@RestController
@RequestMapping("/api/pokemon")
class PokemonController(
    val pokemonService: PokemonService,
){
    @GetMapping()
    fun getPokemon(
        @RequestBody(required = true) pokeSet: PokemonRequest
    ): PokemonResponse  {
        return pokemonService.getPokemon(pokeSet)
    }

}
