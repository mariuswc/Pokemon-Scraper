package org.example.data

data class PokemonResponse(
    val name: String,
    val price: Int? = 10,
    val stockStatus: Boolean? = true
)

