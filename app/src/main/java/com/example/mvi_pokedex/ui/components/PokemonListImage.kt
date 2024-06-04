package com.example.mvi_pokedex.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mvi_pokedex.R

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