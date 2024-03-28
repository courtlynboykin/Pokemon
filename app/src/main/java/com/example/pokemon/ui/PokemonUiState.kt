package com.example.pokemon.ui

import com.example.pokemon.model.Pokemon

sealed interface PokemonUiState {
    data class Success(val pokemon: List<Pokemon>) : PokemonUiState
    object Error : PokemonUiState
    object Loading : PokemonUiState
}