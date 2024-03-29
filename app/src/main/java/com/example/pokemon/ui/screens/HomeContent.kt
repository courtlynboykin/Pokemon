package com.example.pokemon.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonListUiState


@Composable
fun HomePane() {
    val pokemonListViewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)
    DetailPane(uiState = pokemonListViewModel.pokemonUiState) {
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "loading")
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Text(text = "error")
}

@Composable
fun SquadList(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    squadList: List<Pokemon>
) {
//    val uiState by squadViewModel.uiState.collectAsState()
    Column {
        Text(text = "My Squad")
        LazyRow {
            items(squadList.size) {
                PokemonCard(pokemon = pokemon)
            }
        }
    }
}

@Composable
fun DetailPane(
    modifier: Modifier = Modifier,
    uiState: PokemonListUiState,
    retryAction: () -> Unit,
) {
    when (uiState) {
        is PokemonListUiState.Loading -> {
            LoadingScreen()
        }

        is PokemonListUiState.Error -> {
            ErrorScreen(
                retryAction = retryAction
            )
        }

        is PokemonListUiState.Success -> {
            SquadList(pokemon = uiState.pokemon, squadList = uiState.squad)
            PokemonList(
                pokemonList = uiState.pokemon,
            )
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column {
        Text(text = "Pokemon!")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier.padding(horizontal = 4.dp),
            contentPadding = contentPadding,
        ) {
            items(items = pokemonList) { pokemon ->
                PokemonCard(
                    pokemon = pokemon,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Card(modifier = Modifier) {
        Column {
            Text(pokemon.name)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(pokemon.getPokemonImageUrls(pokemon))
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }
    }
}