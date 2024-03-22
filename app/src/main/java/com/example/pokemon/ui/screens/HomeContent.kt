package com.example.pokemon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pokemon.model.Pokemon
import com.example.pokemon.model.PokemonDetails
import com.example.pokemon.model.Sprite
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomePane(
    pokemonUiState: PokemonUiState,
    pokemonDetails: PokemonDetails,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (pokemonUiState) {
        is PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PokemonUiState.Success -> PhotosGridScreen(
            pokemonUiState.pokemon,
            pokemonDetails = pokemonDetails,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )

        is PokemonUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonApp() {
            val pokemonViewModel: PokemonViewModel =
                viewModel(factory = PokemonViewModel.Factory)
    pokemonViewModel.pokemonDetails?.let {
        HomePane(
            pokemonUiState = pokemonViewModel.pokemonUiState,
            pokemonDetails = it,
            retryAction = pokemonViewModel::getPokemon
        )
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
fun PhotosGridScreen(
    pokemen: List<Pokemon>,
    pokemonDetails: PokemonDetails,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = pokemen, key = { pokemon -> pokemon.name }) { pokemon ->
            PokemonCard(
                pokemon,
                pokemonDetails,
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
    pokemonDetails: PokemonDetails,
    modifier: Modifier = Modifier
){
    Card(modifier = Modifier) {
        Column {
            Text(text = pokemon.name)
            pokemonDetails.let{
                val image = it.sprite.frontDefault
                if(image != null){
                        Image(painter = rememberAsyncImagePainter(model = image), contentDescription = "yo")
                    }
                }
            }

//            Text(text = )

        }
    }

@Preview
@Composable
fun PokemonCardPreview(){
    PokemonCard(pokemon = Pokemon(name = "Pikachu", url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"), pokemonDetails = PokemonDetails(sprite = Sprite(frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"), weight = 15))
}