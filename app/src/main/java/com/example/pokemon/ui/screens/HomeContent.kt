package com.example.pokemon.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonListUiState
import com.example.pokemon.ui.PokemonSquadUiState


@Composable
fun SquadList(
    onClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    squadList: List<Pokemon>
) {
//    val uiState by squadViewModel.uiState.collectAsState()
    Column {
        Text(text = "My Squad")
        LazyRow {
            items(squadList.size) {
                PokemonCard(pokemon = pokemon, onClick = onClick)
            }
        }
    }
}

@Composable
fun HomePane(
    viewModel: PokemonSquadViewModel = viewModel(),
) {
    val pokemonViewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)

    val squadUiState by viewModel.uiState.collectAsState()
    Column() {
        squadUiState.pokemon?.let { it ->
            SquadList(
                pokemon = it,
                squadList = squadUiState.pokemonSquad,
                onClick = {
                    viewModel.updatePokemonSelection(it)
                viewModel.removePokemon(it)}
            )
        }
        DetailScreen(
            listUiState = pokemonViewModel.pokemonUiState,
            squadUiState = squadUiState,
            onListClick = {
                viewModel.updatePokemonSelection(it)
                viewModel.addPokemon(it)
            },
            onSquadClick = {
                viewModel.updatePokemonSelection(it)
                viewModel.removePokemon(it)
            }
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "loading")
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(text = "error")
}


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    listUiState: PokemonListUiState,
    squadUiState: PokemonSquadUiState,
    onListClick: (Pokemon) -> Unit,
    onSquadClick: (Pokemon) -> Unit
) {
    when (listUiState) {
        is PokemonListUiState.Loading -> {
            LoadingScreen()
        }

        is PokemonListUiState.Error -> {
            ErrorScreen()
        }

        is PokemonListUiState.Success -> {

            PhotosGridScreen(
                pokemonList = listUiState.pokemon,
                onClick = onListClick,
            )
        }

        }

}

@Composable
fun PhotosGridScreen(
    pokemonList: List<Pokemon>,
    onClick: (Pokemon) -> Unit,
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
                    onClick = onClick,
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
    onClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = Modifier.clickable { onClick(pokemon) }) {
        Column {
            Text(pokemon.name)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(pokemon.getPokemonImageUrls(pokemon))
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
//            Button(onClick = onClick) {
//                Text(text = "Select")
//            }
        }
    }
}