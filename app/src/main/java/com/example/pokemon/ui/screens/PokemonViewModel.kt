package com.example.pokemon.ui.screens

import androidx.compose.runtime.MutableState
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
import com.example.pokemon.model.PokemonDetails
import com.example.pokemon.ui.PokemonApplication
import com.example.pokemon.ui.PokemonDetailsUiState
import com.example.pokemon.ui.PokemonImageUiState
import com.example.pokemon.ui.PokemonUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    var pokemonUiState: PokemonUiState by mutableStateOf(PokemonUiState.Loading)
        private set
   var pokemonDetailsUiState: PokemonDetailsUiState by mutableStateOf(PokemonDetailsUiState.Loading)
    private set

    private val _pokemonImageListUiState = MutableList(151) { mutableStateOf("") }
    val pokemonImageListUiState: List<MutableState<String>> = _pokemonImageListUiState
    init {
        getPokemon()

        getPokemonDetails(object : (List<String>) -> Unit {
            override fun invoke(sprites: List<String>) {
                // Update UI state with the received list of sprites
                _pokemonImageListUiState.addAll(sprites.map { mutableStateOf(it) })
            }
        })
    }



    fun getPokemon(limit: Int = 151){
        viewModelScope.launch {
            pokemonUiState = PokemonUiState.Loading
            pokemonUiState = try {
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

    fun getPokemonDetails(callback: (List<String>) -> Unit) {
        viewModelScope.launch {
            val pokemonList = pokemonRepository.getPokemon(151)
                ?: // Handle error
                return@launch

            val pokemonDetails = pokemonList.mapNotNull { pokemon ->
                try {
                    val id = extractPokemonIdFromUrl(pokemon.url) // Extract ID using your function
                    val details = pokemonRepository.getPokemonDetails(id)
                    details?.sprite.toString() // Assuming sprite is a String property
                } catch (e: IOException) {
                    // Handle or log IOException
                    null // Skip this pokemon if details failed to load
                } catch (e: HttpException) {
                    // Handle or log HttpException
                    null
                }
            }

            withContext(Dispatchers.Main) {
                callback(pokemonDetails) // Call the callback with the list
            }
        }
    }






        private fun extractPokemonIdFromUrl(url: String): Int {
            val parts = url.split("/")
            return parts.getOrNull(parts.size - 2)?.toIntOrNull() ?: 0

    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as PokemonApplication)
                val pokemonRepository = application.pokemonContainer.pokemonRepository
                PokemonViewModel(pokemonRepository = pokemonRepository, )
            }
        }
    }

}