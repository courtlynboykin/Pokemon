package com.example.pokemon.fake

import com.example.pokemon.model.Pokemon

object FakeDataSource {

    val pokemonList = listOf(
        Pokemon("Bulbasaur", "https://example.com/pokemon/1/"),
        Pokemon("Charmander", "https://example.com/pokemon/2/"),
        Pokemon("Squirtle", "https://example.com/pokemon/3/"),
        Pokemon("Pikachu", "https://example.com/pokemon/4/"),
        Pokemon("Meowth", "https://example.com/pokemon/5/")
    )

    fun getFakePokemonList(): List<Pokemon> {
        return pokemonList
    }

    fun addPokemonToSquad(): MutableList<Pokemon> {
        val pokemonSquad = mutableListOf<Pokemon>()
        pokemonSquad.addAll(pokemonList.subList(0,3))
        return pokemonSquad
    }

    fun removePokemonFromSquad(pokemon: Pokemon): MutableList<Pokemon> {
        val updatedPokemonSquad = mutableListOf<Pokemon>()
        updatedPokemonSquad.addAll(pokemonList.subList(0,3))
        updatedPokemonSquad.removeAt(0)
        return updatedPokemonSquad
    }
}