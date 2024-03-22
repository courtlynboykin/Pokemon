package com.example.pokemon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)

@Serializable
data class PokemonDetails(
    val sprite: Sprite,
//    val move: Moves,
    val weight: Int
)
@Serializable
data class Sprite(
    @SerialName(value = "front_default") val frontDefault: String?
)
