package com.example.pokemon.ui

import android.app.Application
import com.example.pokemon.data.DefaultPokemonAppContainer
import com.example.pokemon.data.PokemonAppContainer

class PokemonApplication : Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var pokemonContainer: PokemonAppContainer

    override fun onCreate() {
        super.onCreate()
        pokemonContainer = DefaultPokemonAppContainer()
    }
}