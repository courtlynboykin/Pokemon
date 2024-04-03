package com.example.pokemon.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonApplication
import com.example.pokemon.ui.PokemonListUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class PokemonListViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    var pokemonUiState: PokemonListUiState by mutableStateOf(PokemonListUiState.Loading)
        private set

    private var _allPokemon: List<Pokemon> = emptyList()

    init {
        getPokemon()
        setAllPokemon()
    }

    fun getPokemon(limit: Int = 151) {
        viewModelScope.launch {
            pokemonUiState = PokemonListUiState.Loading
            pokemonUiState = try {
                val pokemon = pokemonRepository.getPokemon(limit)

                if (pokemon == null) {
                    PokemonListUiState.Error
                } else {
                    PokemonListUiState.Success(pokemon)
                }
            } catch (e: IOException) {
                PokemonListUiState.Error
            } catch (e: HttpException) {
                PokemonListUiState.Error
            }
        }
    }

    fun setAllPokemon() {
        viewModelScope.launch {
            val pokemon = pokemonRepository.getPokemon(151)
            if (pokemon != null) {
                _allPokemon = pokemon
            }
        }
    }

    fun searchPokemonByName(name: String) {
        viewModelScope.launch {
            val filteredPokemon = _allPokemon.filter { it.name.contains(name, ignoreCase = true) }
            pokemonUiState = PokemonListUiState.Success(filteredPokemon)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as PokemonApplication)
                val pokemonRepository = application.pokemonContainer.pokemonRepository
                PokemonListViewModel(pokemonRepository = pokemonRepository)
            }
        }
    }
}