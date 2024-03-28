package com.example.pokemon.data

import com.example.pokemon.network.PokemonApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface PokemonAppContainer {
    val pokemonRepository: PokemonRepository
}

// Implementation for the Dependency Injection container at the application level.
//  Variables are initialized lazily and the same instance is shared across the whole app.
class DefaultPokemonAppContainer : PokemonAppContainer {
    private val baseUrl = "https://pokeapi.co/api/v2/"

    private val json = Json {
        ignoreUnknownKeys = true  // Set ignoreUnknownKeys to true
    }

    //Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Retrofit service object for creating api calls
    private val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    // DI implementation for Pokemon repository
    override val pokemonRepository: PokemonRepository by lazy {
        DefaultPokemonRepository(retrofitService)
    }
}
