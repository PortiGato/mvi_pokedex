package com.example.mvi_pokedex.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.mvi_pokedex.R
import kotlinx.coroutines.delay

@Composable
fun ClickableImage(
    mainImageResId: Int,
    focusedImageResId: Int,
    contentDescription: String,
    onClick: () -> Unit = {},
    delay: Long = 1000,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false) }

    Row(verticalAlignment = CenterVertically,
        modifier = Modifier.clickable(
            //Desactivar la interacción al hacer clic
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick.invoke()
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