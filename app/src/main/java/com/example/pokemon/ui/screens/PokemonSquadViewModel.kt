package com.example.pokemon.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonSquadUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokemonSquadViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonSquadUiState())
    val uiState: StateFlow<PokemonSquadUiState> = _uiState.asStateFlow()

    fun addPokemon(pokemon: Pokemon?) {
        val updatedSquad = _uiState.value.pokemonSquad.toMutableList()
        if (pokemon != null) {
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
        Log.d("HomePane", "Updating selection to: $selectedPokemon")
        _uiState.update { currentState ->
            currentState.copy(pokemon = selectedPokemon)
        }
    }
}