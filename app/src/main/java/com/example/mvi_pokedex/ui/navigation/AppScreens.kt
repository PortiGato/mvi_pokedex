package com.example.mvi_pokedex.ui.navigation


sealed class AppScreens(val route: String) {

    //Here all the screens are declared to be used in the navigation
    object HomeScreen : AppScreens(route = "home_screen")
    object DetailScreen : AppScreens(route = "detail_screen")


}