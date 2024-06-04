package com.example.mvi_pokedex.ui.navigation

import com.example.mvi_pokedex.utils.Constants.POKEMON_ID


sealed class AppScreens(val route: String) {

    data object HomeScreen : AppScreens(route = "home_screen")
    data object DetailScreen : AppScreens(route = "detail_screen/{$POKEMON_ID}") {
        fun createRoute(pokemonId: Int) = "detail_screen/$pokemonId"
    }
}
