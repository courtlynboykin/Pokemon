package com.example.pokemon.ui

import com.example.pokemon.model.PokemonDetails

sealed interface PokemonDetailsUiState {

    data class Success (val pokemonDetails: PokemonDetails) : PokemonDetailsUiState
    object Error: PokemonDetailsUiState
    object Loading: PokemonDetailsUiState
}