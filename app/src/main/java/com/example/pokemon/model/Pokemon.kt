package com.example.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class QueryResponse(
    val count: Int,
    val results: List<Pokemon>
)

@Serializable
data class Pokemon(
    val name: String,
    val url: String,
) {
    // Function that returns the image URL for the Pokemon
    fun getPokemonImageUrls(pokemon: Pokemon): String {
        val parts = pokemon.url.split("/")
        val pokemonId = parts.getOrNull(parts.size - 2)?.toIntOrNull() ?: 0
        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
        return imageUrl
    }
}