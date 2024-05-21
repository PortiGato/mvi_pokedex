package com.example.mvi_pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvi_pokedex.ui.screens.home.HomeScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {

        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }


    }
}
