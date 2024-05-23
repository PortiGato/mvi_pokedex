package com.example.mvi_pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvi_pokedex.ui.screens.detail.DetailScreen
import com.example.mvi_pokedex.ui.screens.home.HomeScreen
import com.example.mvi_pokedex.utils.Constants.POKEMON_ID


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {

        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(route = AppScreens.DetailScreen.route,
            arguments = listOf(navArgument(POKEMON_ID) { type =NavType.IntType} )
        ){
            DetailScreen(navController,it.arguments?.getInt(POKEMON_ID) ?:-1)
        }

    }
}
