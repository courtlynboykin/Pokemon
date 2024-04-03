package com.example.pokemon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.R
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.PokemonListUiState
import com.example.pokemon.ui.PokemonSquadUiState
import com.example.pokemon.ui.viewmodels.PokemonListViewModel
import com.example.pokemon.ui.viewmodels.PokemonSquadViewModel


@Composable
fun HomePane(
    viewModel: PokemonSquadViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val pokemonViewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)

    val squadUiState by viewModel.uiState.collectAsState()

    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar()

        },
        modifier = Modifier.padding(16.dp)
    ) { it ->
        Column(modifier = modifier.padding(it)) {
            if (squadUiState.pokemonSquad.size >= 2) {
                BattleButton(onClick = {
                    /*ToDo*/
                })
            }
            SquadList(
                onClick = {
                    viewModel.updatePokemonSelection(it)
                    viewModel.removePokemon(it)
                }
            )
            Divider(
                color = Color(0x37859299),
                thickness = 4.dp,
                modifier = Modifier
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                text = it
                pokemonViewModel.searchPokemonByName(text)
            }
            DetailScreen(
                listUiState = pokemonViewModel.pokemonUiState,
                squadUiState = squadUiState,
                onListClick = {
                    viewModel.updatePokemonSelection(it)
                    viewModel.addPokemon(it)
                }
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(text)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )

    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    listUiState: PokemonListUiState,
    squadUiState: PokemonSquadUiState,
    onListClick: (Pokemon) -> Unit
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
                squadUiState = squadUiState,
                onClick = onListClick,
            )
        }
    }
}

@Composable
fun SquadList(
    onClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonSquadViewModel = viewModel()
) {
    val squadUiState by viewModel.uiState.collectAsState()
    Column {
        Text(
            text = stringResource(R.string.my_squad),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFFBE1306),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 4.dp)
                .fillMaxWidth()
        )
        if (squadUiState.pokemonSquad.isEmpty()) {
            Text(
                text = "No Pokemon in Squad",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        LazyRow {
            items(squadUiState.pokemonSquad.size) {
                PokemonCard(
                    pokemon = squadUiState.pokemonSquad[it],
                    squadUiState = squadUiState,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun PhotosGridScreen(
    pokemonList: List<Pokemon>,
    onClick: (Pokemon) -> Unit,
    squadUiState: PokemonSquadUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column {
        Text(
            text = stringResource(R.string.available),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF004369),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 4.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.tagline),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 2.dp, bottom = 4.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(105.dp),
            modifier = modifier.padding(horizontal = 4.dp),
            contentPadding = contentPadding,
        ) {
            items(items = pokemonList) { pokemon ->
                val inSquad = squadUiState.pokemonSquad.contains(pokemon)
                if (!inSquad) {
                    PokemonCard(
                        pokemon = pokemon,
                        onClick = onClick,
                        squadUiState = squadUiState,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onClick: (Pokemon) -> Unit,
    squadUiState: PokemonSquadUiState,
    modifier: Modifier = Modifier
) {
    val inSquad = squadUiState.pokemonSquad.contains(pokemon)
    Card(
        colors = CardDefaults.cardColors(Color(0xFFECE6D4)),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 10.dp)
        ) {
            Text(
                pokemon.name.uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF52725B),
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(pokemon.getPokemonImageUrls(pokemon))
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .padding(10.dp)
                    .size(88.dp)
            )
            Button(
                onClick = { onClick(pokemon) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!inSquad) Color(0xFF004369) else Color(0xFFBE1306),
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                ),
                modifier = Modifier
                    .size(width = 80.dp, height = 25.dp)
            ) {

                Text(
                    text = if (!inSquad) stringResource(R.string.add) else stringResource(R.string.remove),
                    fontSize = 8.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {

            Image(
                painter = painterResource(R.mipmap.pokemon_logo),
                contentDescription = null,
                modifier = modifier
                    .size(180.dp)
            )
        }
    )
}

@Composable
fun BattleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.battle)
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.loading),
        color = Color.Red,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(32.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.error),
        color = Color.Red,
        modifier = modifier.padding(16.dp)
    )
}