package com.example.mvi_pokedex.ui.screens.home

import androidx.compose.runtime.Immutable
import com.example.mvi_pokedex.domain.model.Pokemon
import com.example.mvi_pokedex.ui.base.BaseViewContract


class HomeContract(): BaseViewContract() {
    //State of HomeScreen
    @Immutable
    data class HomeScreenState(
        val isLoading: Boolean = true,
        val pokemonList: List<Pokemon> = emptyList(),
        val pokemonFilterList: List<Pokemon> = emptyList(),
        val sortSelected: SortOption = SortOption.Ascending,
        val errorMsg: String? = "",
        var searchQuery: String = "",
    ) : UiState()


    // Posible Events (or intents)
    @Immutable
    sealed class HomeScreenUiEvent : UiEvent() {

        data class ShowPokemonFilterItems(val pokemonFilterList: List<Pokemon>) : HomeScreenUiEvent()
        data class SortPokemonList(val sortOption: SortOption) : HomeScreenUiEvent()
        object FetchPokemonList : HomeScreenUiEvent()
        object DismissDialog : HomeScreenUiEvent()
    }


    //Action
    sealed class HomeUiAction: UiAction() {
        data class ShowSnackbar(val msg: String) : HomeUiAction()
    }

}

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