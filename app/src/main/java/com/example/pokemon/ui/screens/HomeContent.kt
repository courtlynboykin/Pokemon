package com.example.pokemon.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.pokemon.ui.PokemonUiState


@Composable
fun HomePane() {
    val pokemonViewModel: PokemonViewModel =
        viewModel(factory = PokemonViewModel.Factory)
    DetailScreen(uiState = pokemonViewModel.pokemonUiState) {
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
fun DetailScreen(
    modifier: Modifier = Modifier,
    uiState: PokemonUiState,
    retryAction: () -> Unit,
) {
    when (uiState) {
        is PokemonUiState.Loading -> {
            LoadingScreen()
        }

        is PokemonUiState.Error -> {
            ErrorScreen(
                retryAction = retryAction
            )
        }

        is PokemonUiState.Success -> {
            PhotosGridScreen(
                pokemonList = uiState.pokemon,
            )
        }
    }
}

@Composable
fun PhotosGridScreen(
    pokemonList: List<Pokemon>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    var index by remember { mutableIntStateOf(0) }
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