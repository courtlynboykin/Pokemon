package com.example.pokemon.ui

import com.example.pokemon.model.Pokemon

sealed interface PokemonListUiState {
    data class Success(val pokemon: List<Pokemon>) : PokemonListUiState
    object Error : PokemonListUiState
    object Loading : PokemonListUiState
}