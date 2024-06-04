package com.example.mvi_pokedex.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mvi_pokedex.R
import com.example.mvi_pokedex.ui.components.CardList
import com.example.mvi_pokedex.ui.components.ClickableImage
import com.example.mvi_pokedex.ui.components.ErrorAlertDialog
import com.example.mvi_pokedex.ui.components.HorizontalRadioGroup
import com.example.mvi_pokedex.ui.components.LocalGifImageView
import com.example.mvi_pokedex.ui.components.LottieAnimationComponent
import com.example.mvi_pokedex.ui.components.SearchBar


@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var searchText by rememberSaveable { mutableStateOf("") }
    val filteredPokemonList = state.pokemonFilterList

    LaunchedEffect(Unit) {
        viewModel.sendEvent(HomeContract.HomeScreenUiEvent.FetchPokemonList)
    }

    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            //Muestra un efecto de enseñar otra imagen y luego dejarla como estaba
            // y vuelve a intentar descargar la lista de pokemon
            ClickableImage(
                mainImageResId = R.drawable.pokemon_logo,
                focusedImageResId = R.drawable.pokemon_logo2,
                contentDescription = stringResource(id = R.string.pokemon_logo),
                onClick = {viewModel.sendEvent(HomeContract.HomeScreenUiEvent.FetchPokemonList)},
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
            )

            SearchBar(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )

            HorizontalRadioGroup(selectedSortOption =
            {
                viewModel.sendEvent(
                    HomeContract.HomeScreenUiEvent.SortPokemonList(
                        it
                    )
                )
            }
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimationComponent(R.raw.pokeball_loading,200.dp)
                }
            } else {

                ErrorAlertDialog(
                    showDialog = state.showDialog,
                    onDismiss = { viewModel.sendEvent(HomeContract.HomeScreenUiEvent.DismissDialog) },
                    description = state.errorMsg ?: stringResource(id = R.string.error_description)
                )
                
                if (filteredPokemonList.isNotEmpty()) {
                    CardList(navController, filteredPokemonList)
                } else {
                    if (searchText.isEmpty()) {
                        CardList(navController, state.pokemonList)
                    } else {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LocalGifImageView(R.drawable.pikachu_dizzy)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = stringResource(id = R.string.result_not_found))
                        }

                    }
                }
            }
            LaunchedEffect(searchText) {
                val filteredList = state.pokemonList.filter {
                    it.name.contains(searchText, ignoreCase = true)
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












