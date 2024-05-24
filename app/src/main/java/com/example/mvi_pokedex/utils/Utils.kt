package com.example.mvi_pokedex.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

}