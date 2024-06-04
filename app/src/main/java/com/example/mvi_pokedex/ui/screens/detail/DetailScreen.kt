package com.example.mvi_pokedex.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mvi_pokedex.R
import com.example.mvi_pokedex.ui.components.ErrorAlertDialog
import com.example.mvi_pokedex.ui.components.MovingIconScreen
import com.example.mvi_pokedex.ui.components.StatRow
import com.example.mvi_pokedex.ui.components.SwipeGestureDetector
import com.example.mvi_pokedex.ui.screens.home.LottieAnimationComponent
import com.example.mvi_pokedex.ui.screens.home.PokemonListImage
import com.example.mvi_pokedex.ui.theme.AtkColor
import com.example.mvi_pokedex.ui.theme.DefColor
import com.example.mvi_pokedex.ui.theme.HPColor
import com.example.mvi_pokedex.ui.theme.SpAtkColor
import com.example.mvi_pokedex.ui.theme.SpDefColor
import com.example.mvi_pokedex.ui.theme.SpdColor
import com.example.mvi_pokedex.utils.Constants.FIRST_POKEMON
import com.example.mvi_pokedex.utils.Constants.NUM_POKEMONS
import com.example.mvi_pokedex.utils.Utils.getColorByName
import kotlinx.coroutines.delay


@Composable
fun DetailScreen(navController: NavHostController,pokemonId: Int) {

    val viewModel: DetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var currentPokemonId by rememberSaveable { mutableIntStateOf(pokemonId) }
    val updatePokemonDetails: (Int) -> Unit = { id ->
        viewModel.sendEvent(DetailContract.DetailScreenUiEvent.SetIDPokemon(id))
    }

    LaunchedEffect(key1 = currentPokemonId) {
        updatePokemonDetails(currentPokemonId)
    }

    //Detecta el gesto de Swipe para poder avanzar o retroceder sobre los pokemons
    SwipeGestureDetector(
        onSwipeLeft = {
            //El último pokemon es el 151
            if (currentPokemonId < NUM_POKEMONS) {
                currentPokemonId++
            }
        },
        onSwipeRight = {
            //El primer pokemon es el 1
            if (currentPokemonId > FIRST_POKEMON) {
                currentPokemonId--
            }
        }
    )

    if (state.isLoading) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            LottieAnimationComponent(R.raw.pokeball_loading, 200.dp)
        }

    } else {

        if (state.showDialog) {
            ErrorAlertDialog(
                showDialog = true,
                onDismiss = {
                    navController.popBackStack()
                    viewModel.sendEvent(DetailContract.DetailScreenUiEvent.DismissDialog)
                },
                description = state.errorMsg ?: stringResource(id = R.string.error_description)
            )

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = CenterHorizontally
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 32.dp,
                                    bottomEnd = 32.dp
                                )
                            )
                            .background(getColorByName(state.pokemonDetail?.types?.first() ?: "")),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.pokemon_id,
                                    state.pokemonDetail?.id ?: 0
                                ),
                                modifier = Modifier.padding(start = 16.dp),
                                fontSize = 20.sp,
                                fontWeight = Bold
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            IconNoRippleClickable(
                                Icons.Default.Close,
                                stringResource(id = R.string.close)
                            ) { navController.popBackStack() }
                        }
                        state.pokemonDetail?.let { PokemonListImage(it.imgURL, 200.dp) }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.pokemonDetail?.name.orEmpty(),
                        fontSize = 30.sp,
                        fontWeight = Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //Si solo tiene un tipo solo se pinta una sola Card,
                    // si tiene 2 o mas pongo una LazyVerticalGrid de 2 columnas para que lo gestione
                    if (state.pokemonDetail?.types.orEmpty().size >= 2) {
                        TypeList(state.pokemonDetail?.types.orEmpty())
                    } else {
                        state.pokemonDetail?.types?.first()?.let { TypeCardItem(it, 150.dp) }
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.height_meter,
                                    state.pokemonDetail?.height.toString()
                                ),
                                fontSize = 20.sp,
                                fontWeight = Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.height),
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(64.dp))
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.weight_kg,
                                    state.pokemonDetail?.weight.toString()
                                ),
                                fontSize = 20.sp,
                                fontWeight = Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.weight),
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {

                        Text(
                            text = stringResource(id = R.string.stats),
                            fontSize = 30.sp,
                            fontWeight = Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        StatRow(
                            text = "HP",
                            progress = state.pokemonDetail?.hp ?: 0,
                            progressColor = HPColor
                        )
                        StatRow(
                            text = "ATK",
                            progress = state.pokemonDetail?.attack ?: 0,
                            progressColor = AtkColor
                        )
                        StatRow(
                            text = "DEF",
                            progress = state.pokemonDetail?.defense ?: 0,
                            progressColor = DefColor
                        )
                        StatRow(
                            text = "SP",
                            progress = state.pokemonDetail?.speed ?: 0,
                            progressColor = SpdColor
                        )
                        StatRow(
                            text = "SA",
                            progress = state.pokemonDetail?.specialAttack ?: 0,
                            progressColor = SpAtkColor
                        )
                        StatRow(
                            text = "SD",
                            progress = state.pokemonDetail?.specialDefense ?: 0,
                            progressColor = SpDefColor
                        )

                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        //Podría poner unas flechas laterales para indicar que se puede hacer swipe pero...
                        //¡¡Animación!!
                        MovingIconScreen()
                    }

                }
            }
        }
    }
}

@Composable
fun TypeList(pokemonTypes: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {

        items(pokemonTypes) { item ->
            TypeCardItem(item)
        }
    }
}

@Composable
private fun TypeCardItem(type: String, widthType: Dp = Dp.Unspecified) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(widthType),
        colors = CardDefaults.cardColors(containerColor = getColorByName(type))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = type,
                Modifier.padding(8.dp),
                fontSize = 20.sp,
                fontWeight = Bold
            )
        }
    }
}

@Composable
private fun IconNoRippleClickable(
    imageVector: ImageVector,
    contentDescription: String,
    size: Dp = 50.dp,
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .size(size)
            .padding(top = 16.dp, end = 16.dp)
            //Desactiva el efecto sombra al pulsar sobre la X de cerrar
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onClick()
            }
    )
}