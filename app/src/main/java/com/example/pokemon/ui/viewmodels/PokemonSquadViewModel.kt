package com.example.pokemon.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonSquadUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PokemonSquadViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonSquadUiState())
    val uiState: StateFlow<PokemonSquadUiState> = _uiState.asStateFlow()

    fun addPokemon(pokemon: Pokemon?) {
        val updatedSquad = _uiState.value.pokemonSquad.toMutableList()
        if (pokemon != null && updatedSquad.size < 6) {
            updatedSquad.add(pokemon)
        }
        _uiState.value = _uiState.value.copy(pokemonSquad = updatedSquad)
    }

    fun removePokemon(pokemon: Pokemon?) {
        val updatedSquad = _uiState.value.pokemonSquad.toMutableList()
        updatedSquad.remove(pokemon)
        _uiState.value = _uiState.value.copy(pokemonSquad = updatedSquad)
    }

    fun updatePokemonSelection(selectedPokemon: Pokemon) {
        _uiState.value = _uiState.value.copy(pokemon = selectedPokemon)
    }
}