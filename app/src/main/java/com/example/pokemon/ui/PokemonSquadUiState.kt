package com.example.pokemon.ui

import com.example.pokemon.model.Pokemon

data class PokemonSquadUiState (
    val pokemonSquad: List<Pokemon> = emptyList(),
    val pokemon: Pokemon? = null,
)