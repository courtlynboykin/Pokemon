package com.example.pokemon.data

import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetails
import com.example.pokemon.network.PokemonApiService


// Network implementation of repository that retrieves pokemon data from the data source
interface PokemonRepository {
    suspend fun getPokemon(limit: Int): List<Pokemon>?
    suspend fun getPokemonDetails(id: Int): PokemonDetails?

}
class DefaultPokemonRepository(private val pokemonApiService: PokemonApiService) : PokemonRepository {
    // Retrieves list of pokemon from the data source
    override suspend fun getPokemon(limit: Int): List<Pokemon>? {
        return try {
            val res = pokemonApiService.getPokemonResults(151)
            if (res.isSuccessful) {
                res.body()?.results ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Retrieves a information about one Pokemon
    override suspend fun getPokemonDetails(id: Int): PokemonDetails?{
        val res = pokemonApiService.getPokemon(id)
        return res.body()
    }



}

