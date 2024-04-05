package com.example.pokemon.test


import android.provider.ContactsContract.Contacts.Photo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.pokemon.ui.PokemonSquadUiState
import com.example.pokemon.ui.screens.PhotosGridScreen
import com.example.pokemon.ui.screens.SquadList
import com.example.pokemon.ui.theme.PokemonTheme
import org.junit.Rule
import org.junit.Test

class PokemonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialScreen_displaysSquadText() {
        composeTestRule.setContent {
            PokemonTheme {
                SquadList(onClick = {})
            }
        }
        composeTestRule.onNodeWithText("My Squad").assertExists()
    }


    @Test
    fun initialScreen_displaysListText() {
        composeTestRule.setContent {
            PokemonTheme {
                PhotosGridScreen(pokemonList = listOf(), onClick = {}, squadUiState = PokemonSquadUiState())
            }
        }
        composeTestRule.onNodeWithText("Available Pokemon").assertExists()
    }
}