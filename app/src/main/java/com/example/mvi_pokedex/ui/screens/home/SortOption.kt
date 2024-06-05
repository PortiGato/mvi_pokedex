package com.example.mvi_pokedex.ui.screens.home

sealed class SortOption(val label: String) {
    data object Ascending : SortOption("Asc")
    data object Descending : SortOption("Desc")
    data object AlphabeticalAZ : SortOption("A-Z")
    data object AlphabeticalZA : SortOption("Z-A")

    companion object {
        // Si no usamos el lazy el primer elemento de la lista no logra inicializarse y es siempre null
        // Solución : https://stackoverflow.com/a/78188028
        val options by lazy { listOf(Ascending, Descending, AlphabeticalAZ, AlphabeticalZA) }
    }
}