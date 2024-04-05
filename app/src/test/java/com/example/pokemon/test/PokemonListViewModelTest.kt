package com.example.pokemon.test

import com.example.pokemon.fake.FakeDataSource
import com.example.pokemon.fake.FakeNetworkPokemonRepository
import com.example.pokemon.rules.TestDispatcherRule
//import com.example.pokemon.rules.TestDispatcherRule
import com.example.pokemon.ui.PokemonListUiState
import com.example.pokemon.ui.viewmodels.PokemonListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class PokemonListViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun pokemonListViewModel_getPokemonList_verifyPokemonListUiStateSuccess() =
        runTest {
            val pokemonListViewModel = PokemonListViewModel(
                pokemonRepository = FakeNetworkPokemonRepository()
            )
            assertEquals(
                PokemonListUiState.Success(FakeDataSource.getFakePokemonList()),
                pokemonListViewModel.pokemonUiState
            )
        }
}
