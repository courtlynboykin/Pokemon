package com.example.pokemon.data

import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetails
import com.example.pokemon.network.PokemonApiService
import retrofit2.http.GET

interface PokemonRepository {
    @GET("pokemon")
    suspend fun getPokemon(): List<Pokemon>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(id: Int): PokemonDetails
}

// Network implementation of repository that retrieves pokemon data from the data source
class DefaultPokemonRepository(private val pokemonApiService: PokemonApiService) : PokemonRepository {
    // Retrieves list of pokemon from the data source
    override suspend fun getPokemon(): List<Pokemon> = pokemonApiService.getPokemon(limit = 151, offset = 0)

    override suspend fun getPokemonDetails(id: Int): PokemonDetails = pokemonApiService.getPokemonDetails(id)
}