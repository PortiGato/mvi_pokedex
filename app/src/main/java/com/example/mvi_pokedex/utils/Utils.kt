package com.example.mvi_pokedex.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import com.example.mvi_pokedex.ui.theme.Bug
import com.example.mvi_pokedex.ui.theme.Dark
import com.example.mvi_pokedex.ui.theme.Dragon
import com.example.mvi_pokedex.ui.theme.Electric
import com.example.mvi_pokedex.ui.theme.Fairy
import com.example.mvi_pokedex.ui.theme.Fighting
import com.example.mvi_pokedex.ui.theme.Fire
import com.example.mvi_pokedex.ui.theme.Flying
import com.example.mvi_pokedex.ui.theme.Ghost
import com.example.mvi_pokedex.ui.theme.Grass
import com.example.mvi_pokedex.ui.theme.Ground
import com.example.mvi_pokedex.ui.theme.Ice
import com.example.mvi_pokedex.ui.theme.Normal
import com.example.mvi_pokedex.ui.theme.Poison
import com.example.mvi_pokedex.ui.theme.Psychic
import com.example.mvi_pokedex.ui.theme.Rock
import com.example.mvi_pokedex.ui.theme.Steel
import com.example.mvi_pokedex.ui.theme.Water
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale

object Utils {
    fun isDeviceOnline(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    fun replaceFirstChar(t: String): String {
        return t.replaceFirstChar {
            // Cada nombre con Mayuscula la primera letra
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    fun getColorByName(name: String): Color {
        return when (name.lowercase()) {
            "bug" -> Bug
            "dark" -> Dark
            "dragon" -> Dragon
            "electric" -> Electric
            "fairy" -> Fairy
            "fighting" -> Fighting
            "fire" -> Fire
            "flying" -> Flying
            "ghost" -> Ghost
            "grass" -> Grass
            "ground" -> Ground
            "ice" -> Ice
            "normal" -> Normal
            "poison" -> Poison
            "psychic" -> Psychic
            "rock" -> Rock
            "steel" -> Steel
            "water" -> Water
            else -> Color.LightGray
        }
    }

    fun getColorsByNames(names: List<String>): List<Color> {
        return names.map { getColorByName(it) }
    }

}