package com.example.pokemon.data

import com.example.pokemon.model.Pokemon
import retrofit2.http.GET

interface Repository {
    @GET("pokemon")
    suspend fun getPokemon(): List<Pokemon>
}