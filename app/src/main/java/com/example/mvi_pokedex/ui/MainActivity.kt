package com.example.mvi_pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mvi_pokedex.ui.navigation.AppNavigation
import com.example.mvi_pokedex.ui.theme.Mvi_Pokedex_Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mvi_Pokedex_Theme {
                AppNavigation()
            }
        }
    }
}