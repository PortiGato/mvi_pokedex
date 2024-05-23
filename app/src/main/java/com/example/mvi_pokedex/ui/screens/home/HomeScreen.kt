package com.example.mvi_pokedex.ui.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mvi_pokedex.R
import com.example.mvi_pokedex.domain.model.Pokemon
import com.example.mvi_pokedex.ui.navigation.AppScreens


@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val uiAction by viewModel.actions.collectAsState(null)
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val filteredPokemonList = state.pokemonFilterList

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )

            Spacer(modifier = Modifier.height(8.dp))
            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (filteredPokemonList.isNotEmpty()) {
                    CardList(navController, filteredPokemonList)
                } else {
                    if (searchText.text.isEmpty()) {
                        CardList(navController, state.pokemonList)
                    } else {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = stringResource(id = R.string.result_not_found))
                        }

                    }
                }
            }
            LaunchedEffect(searchText) {
                val filteredList = state.pokemonList.filter {
                    it.name.contains(searchText.text, ignoreCase = true)
                }
                viewModel.sendEvent(
                    HomeContract.HomeScreenUiEvent.ShowPokemonFilterItems(
                        filteredList
                    )
                )
            }

        }
    }

}

@Composable
fun CardList(navController: NavHostController, pokemonList: List<Pokemon>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

        items(pokemonList) { item ->
            CardItemView(item, navController)
        }
    }
}

@Composable
fun CardItemView(item: Pokemon, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(AppScreens.DetailScreen.createRoute(item.id))
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                PokemonImage(item.imageUrl)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        //text = "#${item.id}",
                        text = stringResource(id = R.string.pokemon_id,item.id),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = item.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

    }
}

@Composable
fun PokemonImage(imageURL: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(500)
            .build(),
        stringResource(id = R.string.pokemon_name),
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            //.background(Color.Gray.copy(alpha = 0.1f))
            .padding(8.dp),
        error = painterResource(R.drawable.pokebola),
        placeholder = painterResource(R.drawable.pokebola))
}


@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onSearchTextChanged: (TextFieldValue) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                onSearchTextChanged(newText)
            },
            label = { Text(text = stringResource(id = R.string.pokemon_name)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Transparent),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.pokemon_name),
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}
