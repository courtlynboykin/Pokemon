package com.example.pokemon.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemon.model.Pokemon
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.PokemonDetails
import com.example.pokemon.ui.PokemonApplication
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PokemonUiState {
    data class Success(val pokemon: List<Pokemon>) : PokemonUiState
    object Error : PokemonUiState
    object Loading : PokemonUiState
}

class PokemonViewModel(val pokemonRepository: PokemonRepository) : ViewModel() {

    var pokemonUiState: PokemonUiState by mutableStateOf<PokemonUiState>(PokemonUiState.Loading)
            private set
    var pokemonList: List<Pokemon> by mutableStateOf(emptyList())
        private set

    var selectedPokemon: Pokemon? by mutableStateOf(null)
        private set

    var pokemonDetails: PokemonDetails? by mutableStateOf(null)
        private set

    init {
        getPokemon()
    }

    fun getPokemon(){
        viewModelScope.launch {
            pokemonUiState = PokemonUiState.Loading
            pokemonUiState = try {
                PokemonUiState.Success(
                    pokemonRepository.getPokemon()
                )
            } catch (e: IOException) {
                PokemonUiState.Error
            } catch (e: HttpException) {
                PokemonUiState.Error
            }
        }



   fun getPokemonDetails(pokemonUrl: String) {
        viewModelScope.launch {
            try {
                // Extract Pokémon ID from URL
                val pokemonId = pokemonUrl.substringAfterLast("/").toInt()
                // Fetch Pokémon details based on ID
                pokemonDetails = pokemonRepository.getPokemonDetails(pokemonId)
            } catch (e: IOException) {
                // Handle IO exception
            } catch (e: HttpException) {
                // Handle HTTP exception
            }
        }
    }

        fun setSelectedPokemon(pokemon: Pokemon) {
            selectedPokemon = pokemon
            getPokemonDetails(pokemon.url)
        }
}

//    var pokemonUiState: PokemonUiState by mutableStateOf<PokemonUiState>(PokemonUiState.Loading)
//        private set
//
//    var pokemonDetails: PokemonDetails? by mutableStateOf(null)
//        private set
//
//    init {
//        getPokemon()
//        val pokemonId = pokemon.url.substringAfterLast("/").toInt()
//        getPokemonDetails(pokemonId)
//    }
//
//
//
//    }
//
//    fun getPokemonDetails(id: Int) {
//        viewModelScope.launch {
//            try {
//                pokemonDetails = pokemonRepository.getPokemonDetails(id)
//            } catch (e: IOException) {
//                PokemonUiState.Error
//            } catch (e: HttpException) {
//                PokemonUiState.Error
//            }
//        }
//    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as PokemonApplication)
                val pokemonRepository = application.pokemonContainer.pokemonRepository
                PokemonViewModel(pokemonRepository = pokemonRepository,  )
            }
        }
    }

}