package com.example.mvi_pokedex.ui.navigation

import com.example.mvi_pokedex.domain.model.CardItem
import java.text.Normalizer


sealed class AppScreens(val route: String) {

    object HomeScreen : AppScreens(route = "home_screen")
    object DetailScreen : AppScreens(route = "detail_screen/{pokemonID}") {
        fun createRoute(pokemonId: String) = "detail_screen/$pokemonId"
    }
}
