package com.example.pokemon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryResponse(
    val count: Int,
    val results: List<Pokemon>
)

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)


@Serializable
data class PokemonDetails(
    @SerialName("sprites") val sprite: PokemonImage
)
@Serializable
data class PokemonImage(
    @SerialName("front_default") val image: String
)