package com.example.mvi_pokedex.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.coroutineScope
import kotlin.math.abs

@Composable
fun SwipeGestureDetector(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    // Umbral de distancia para considerar un swipe válido
    swipeThreshold: Float = 100f
) {
    var offsetX by rememberSaveable { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    detectDragGestures(
                        onDragEnd = {
                            if (abs(offsetX) > swipeThreshold) {
                                if (offsetX > 0) {
                                    onSwipeRight()
                                } else {
                                    onSwipeLeft()
                                }
                            }
                            offsetX = 0f // Reiniciar el desplazamiento
                        }
                    ) { change, dragAmount ->
                        offsetX += dragAmount.x
                        change.consume()
                    }
                }
            }
    )
}