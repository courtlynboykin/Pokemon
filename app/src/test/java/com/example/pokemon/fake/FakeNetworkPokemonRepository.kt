package com.example.pokemon.fake

import com.example.pokemon.data.PokemonRepository
import com.example.pokemon.model.Pokemon

class FakeNetworkPokemonRepository : PokemonRepository {
    override suspend fun getPokemon(limit: Int): List<Pokemon> {
        return FakeDataSource.getFakePokemonList()
    }
}