package com.example.pokemon.network

import com.example.pokemon.model.PokemonDetails
import com.example.pokemon.model.PokemonImage
import com.example.pokemon.model.QueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonResults(@Query("limit") limit: Int = 151): Response<QueryResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<PokemonDetails>
}