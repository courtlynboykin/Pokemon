package com.example.pokemon.test

import com.example.pokemon.fake.FakeDataSource
import com.example.pokemon.rules.TestDispatcherRule
import com.example.pokemon.ui.PokemonSquadUiState
import com.example.pokemon.ui.viewmodels.PokemonSquadViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class PokemonSquadViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun pokemonSquadViewModel_addPokemonToSquad_verifyPokemonSquadUiState() =
        runTest {
            val pokemonSquadViewModel = PokemonSquadViewModel()
            for (pokemon in FakeDataSource.pokemonList.subList(0, 3)) {
                pokemonSquadViewModel.addPokemon(pokemon = pokemon)
            }

            assertEquals(
                PokemonSquadUiState(pokemonSquad = FakeDataSource.addPokemonToSquad()),
                pokemonSquadViewModel.uiState.value
            )
        }

    @Test
    fun pokemonSquadViewModel_removePokemonFromSquad_verifyPokemonSquadUiState() =
        runTest {
            val pokemonSquadViewModel = PokemonSquadViewModel()
            for (pokemon in FakeDataSource.pokemonList.subList(0, 3)) {
                pokemonSquadViewModel.addPokemon(pokemon = pokemon)
            }
            pokemonSquadViewModel.removePokemon(pokemon = FakeDataSource.pokemonList[0])

            assertEquals(
                PokemonSquadUiState(pokemonSquad = FakeDataSource.removePokemonFromSquad(pokemon = FakeDataSource.pokemonList[0])),
                pokemonSquadViewModel.uiState.value
            )
        }
}