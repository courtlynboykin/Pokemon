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
import com.example.pokemon.ui.PokemonUiState

//@Composable
//fun PokemonDetailsScreen() {
//    val viewModel: PokemonViewModel =
//        viewModel(factory = PokemonViewModel.Factory)
//    val pokemonDetails by viewModel.pokemonDetails.observeAsState()
//
//    // Check if pokemonDetails is not null
//    if (pokemonDetails != null) {
//        // Access sprite URLs
//        val frontDefaultUrl = pokemonDetails!!.sprite.frontDefault
////        val frontShinyUrl = pokemonDetails!!.sprite.frontShiny
//
//        // Load sprites using Coil or Picasso
//        // For example:
//        if (!frontDefaultUrl.isNullOrEmpty()) {
//            AsyncImage(
//                model = ImageRequest.Builder(context = LocalContext.current).data(frontDefaultUrl)
//                    .crossfade(true).build(),
//                contentDescription = null
//            )
//        }
//    }
//}
//@Composable
//fun HomePane(
//    pokemonUiState: PokemonUiState,
//    pokemonDetails: PokemonDetails,
//    retryAction: () -> Unit,
//    modifier: Modifier = Modifier,
//    contentPadding: PaddingValues = PaddingValues(0.dp),
//) {
//    when (pokemonUiState) {
//        is PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
//        is PokemonUiState.Success -> PhotosGridScreen(
//            pokemonUiState.pokemon,
//            pokemonDetails = pokemonDetails,
//            contentPadding = contentPadding,
//            modifier = modifier.fillMaxWidth()
//        )
//
//        is PokemonUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PokemonApp() {
//            val pokemonViewModel: PokemonViewModel =
//                viewModel(factory = PokemonViewModel.Factory)
//            HomePane(
//                pokemonUiState = pokemonViewModel.pokemonUiState,
//                pokemonDetails = pokemonViewModel.pokemonUiState.pokemonDetails,
//                retryAction = pokemonViewModel::getPokemon
//            )
//        }
//
//

@Composable
fun HomePane(){
    val pokemonViewModel: PokemonViewModel =
        viewModel(factory = PokemonViewModel.Factory)

    val pokemonImageListUiState by pokemonViewModel.pokemonImageUrls.collectAsState()

    DetailScreen(uiState = pokemonViewModel.pokemonUiState, imageUiState = pokemonImageListUiState, /*detailUiState = pokemonViewModel.pokemonDetailsUiState*/) {
        
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
    imageUiState: List<String>,
//    detailUiState: PokemonDetailsUiState,
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
            // Check if both uiState and detailUiState are ready
//            if (detailUiState is PokemonDetailsUiState.Success) {
                // Render the screen with both UI states
                PhotosGridScreen(
                    pokemonList = uiState.pokemon,
//                    uiState = imageUiState,
                    pokemonImages = imageUiState
                )
//            } else {
//                // If detailUiState is not yet ready, show a loading placeholder
//                LoadingScreen()
//            }
        }
    }
}




    @Composable
    fun PhotosGridScreen(
        pokemonList: List<Pokemon>,
        pokemonImages: List<String>,
//        uiState: List<MutableState<String>>,
//        pokemonDetails: PokemonDetails,
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
               image = pokemonImages[index],
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
                     index++
            }
        }
    }
//if (index < 151) {
//                val imageUrl = pokemonImages[index]
//        Column {
//                    Text(text = pokemon.name)
//                    AsyncImage(
//                        model = ImageRequest.Builder(context = LocalContext.current)
//                            .data(imageUrl)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = null
//                    )
//                }

    @Composable
    fun PokemonCard(
        pokemon: Pokemon,
        image: String,
        modifier: Modifier = Modifier
    ) {
        Card(modifier = Modifier) {
            Column {
                Text(pokemon.name)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            }
        }
    }


//@Preview
//@Composable
//fun PokemonDetailsPreview() {
//    PokemonCard()
//}