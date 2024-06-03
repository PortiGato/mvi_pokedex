package com.example.mvi_pokedex.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mvi_pokedex.R
import kotlinx.coroutines.delay

@Composable
fun MovingIconScreen() {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val offsetAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        IconWithEffect(
            icon = Icons.Default.TouchApp,
            contentDescription = stringResource(id = R.string.swipeable),
            modifier = Modifier
                .offset(x = offsetAnimation.dp)
                .size(50.dp)
        )
    }
}

@Composable
fun IconWithEffect(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint : Color = MaterialTheme.colorScheme.onBackground,
    contentDescription : String
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}