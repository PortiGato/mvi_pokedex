package com.example.mvi_pokedex.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mvi_pokedex.R
import com.example.mvi_pokedex.domain.model.Pokemon
import com.example.mvi_pokedex.ui.components.LocalGifImageView
import com.example.mvi_pokedex.ui.navigation.AppScreens
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
//    val uiAction by viewModel.actions.collectAsState(null)
    var searchText by rememberSaveable { mutableStateOf("") }
    val filteredPokemonList = state.pokemonFilterList

    LaunchedEffect(Unit) {
        viewModel.sendEvent(HomeContract.HomeScreenUiEvent.FetchPokemonList)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            ClickableImage(
                mainImageResId = R.drawable.pokemon_logo,
                focusedImageResId = R.drawable.pokemon_logo2,
                contentDescription = stringResource(id = R.string.pokemon_logo),
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
            )

            SearchBar(
                searchText = searchText,
                onSearchTextChanged = { searchText = it }
            )

            HorizontalRadioGroup(viewModel)

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

@Composable
fun HorizontalRadioGroup(viewModel: HomeViewModel) {
    var selectedOption by rememberSaveable { mutableIntStateOf(0) }
    val options = SortOption.options

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEachIndexed { index,option ->
            Row(verticalAlignment = CenterVertically) {
                RadioButton(
                    selected = selectedOption == index,
                    onClick = {
                        selectedOption = index
                        viewModel.sendEvent(
                            HomeContract.HomeScreenUiEvent.SortPokemonList(
                                option
                            )
                        )
                    },
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = option.label,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp)
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
                PokemonListImage(item.imageUrl, 100.dp)
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
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
fun PokemonListImage(imageURL: String, size: Dp = 100.dp) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(500)
            .build(),
        stringResource(id = R.string.pokemon_name),
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .padding(8.dp),
        error = painterResource(R.drawable.pokebola),
        placeholder = painterResource(R.drawable.pokebola))
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.pokemon_name),
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.erase),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onSearchTextChanged("")
                            }
                    )
                }
            }
        )
    }
}

@Composable
fun ClickableImage(
    mainImageResId: Int,
    focusedImageResId: Int,
    contentDescription: String,
    delay: Long = 1000,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false) }

    Row(verticalAlignment = CenterVertically,
        modifier = Modifier.clickable(
            //Desactivar la interacción al hacer clic
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            isClicked = true
            isFocused = true
        }
    ) {
        Box(modifier = modifier) {
            AnimatedContent(
                targetState = isFocused,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith
                            fadeOut(animationSpec = tween(durationMillis = 300))
                }, label = ""
            ) { targetState ->
                Image(
                    painter = painterResource(id = if (targetState) focusedImageResId else mainImageResId),
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            if (isFocused) {
                // Volver al estado inicial después de 2 segundos
                LaunchedEffect(isClicked) {
                    if (isClicked) {
                        delay(delay)
                        isClicked = false
                        isFocused = false
                    }
                }
            }
        }
        Icon(
            imageVector = Icons.Default.TouchApp,
            contentDescription = stringResource(id = R.string.touch)
        )
    }
}

@Composable
fun LottieAnimationComponent(resLottie: Int, size: Dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resLottie))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(size)
    )
}