package com.example.pokemon.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonApplication
import com.example.pokemon.ui.PokemonDetailsUiState
import com.example.pokemon.ui.PokemonUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    var pokemonUiState: PokemonUiState by mutableStateOf(PokemonUiState.Loading)
        private set

    var selectedPokemonId by mutableIntStateOf(0)
   var pokemonDetailsUiState: PokemonDetailsUiState by mutableStateOf(PokemonDetailsUiState.Loading)
    private set

    init {
            getPokemon()
            fetchPokemonData()

    }



    fun getPokemon(limit: Int = 151){
        viewModelScope.launch {
            pokemonUiState = PokemonUiState.Loading
            try {
                val pokemon = pokemonRepository.getPokemon(limit)
                if (pokemon == null){
                    PokemonUiState.Error
                } else {
                    PokemonUiState.Success(pokemon)
                }
            } catch (e: IOException){
                PokemonUiState.Error
            } catch (e: HttpException){
                PokemonUiState.Error
            }

        }
    }

//    fun getPokemonDetails(id: Int) {
//        viewModelScope.launch {
//            pokemonDetailsUiState = PokemonDetailsUiState.Loading
//            pokemonDetailsUiState = try {
//                val pokemonDetails = pokemonRepository.getPokemonDetails(id)
//                if (pokemonDetails == null) {
//                    PokemonDetailsUiState.Error
//                } else {
//                    PokemonDetailsUiState.Success(pokemonDetails)
//                }
//            } catch (e: IOException) {
//                PokemonDetailsUiState.Error
//            } catch (e: HttpException) {
//                PokemonDetailsUiState.Error
//            }
//
//        }
//    }

      fun fetchPokemonData() {
            viewModelScope.launch {
                val pokemonList = pokemonRepository.getPokemon(151)
                if (pokemonList == null) {
                    pokemonUiState = PokemonUiState.Error
                    return@launch
                }

                val pokemonUrls = pokemonList.map { it.url }
                val extractedIds = mutableListOf<Int>()
                for (url in pokemonUrls) {
                    val extractedId = extractPokemonIdFromUrl(url)
                    extractedIds.add(extractedId)
                }

                // Launch separate coroutines to fetch details for each ID
                extractedIds.forEach { id ->
                    viewModelScope.launch {
                        val pokemonDetails = pokemonRepository.getPokemonDetails(id)
                        // Update UI or store details (implementation based on your needs)
                    }
                }
            }
        }
        private fun extractPokemonIdFromUrl(url: String): Int {
            val parts = url.split("/")
            return parts.getOrNull(parts.size - 2)?.toIntOrNull() ?: 0

    }

//
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