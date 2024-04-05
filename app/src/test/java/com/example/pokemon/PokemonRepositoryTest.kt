package com.example.pokemon

import com.example.pokemon.data.DefaultPokemonRepository
import com.example.pokemon.fake.FakeDataSource
import com.example.pokemon.fake.FakePokemonApiService
import com.example.pokemon.model.QueryResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class PokemonRepositoryTest {
    @Test
    fun pokemonRepository_getPokemon_verifyPokemonList() = runTest {
        val fakeApiService = FakePokemonApiService()

        // Set the expected response
        val expectedResponse = Response.success(QueryResponse(results = FakeDataSource.pokemonList, count = 5))
        fakeApiService.setResponse(expectedResponse)

        val repository = DefaultPokemonRepository(
            pokemonApiService = fakeApiService
        )

        assertEquals(FakeDataSource.pokemonList, repository.getPokemon(5))
    }
}