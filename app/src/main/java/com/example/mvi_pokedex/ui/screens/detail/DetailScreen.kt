package com.example.mvi_pokedex.ui.screens.detail

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mvi_pokedex.R


@Composable
fun DetailScreen(navController: NavHostController,pokemonId: Int) {

    val viewModel: DetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.sendEvent(DetailContract.DetailScreenUiEvent.SetIDPokemon(pokemonId))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            IconNoRippleClickable(
                Icons.Default.Close,
                stringResource(id = R.string.close)
            ) { navController.popBackStack() }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = state.pokemonDetail?.name.orEmpty())
        }
    }
}

@Composable
private fun IconNoRippleClickable(
    imageVector: ImageVector,
    contentDescription: String,
    size: Dp = 75.dp,
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size)
            .padding(16.dp)
            //Desactiva el efecto sombra al pulsar sobre la X de cerrar
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onClick()
            }
    )
}