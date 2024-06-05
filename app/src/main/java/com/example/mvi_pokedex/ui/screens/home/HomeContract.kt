package com.example.mvi_pokedex.ui.screens.home

import androidx.compose.runtime.Immutable
import com.example.mvi_pokedex.domain.model.Pokemon
import com.example.mvi_pokedex.ui.base.BaseViewContract


class HomeContract: BaseViewContract() {
    //State of HomeScreen
    @Immutable
    data class HomeScreenState(
        val isLoading: Boolean = true,
        val showDialog: Boolean = false,
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
        data object FetchPokemonList : HomeScreenUiEvent()
        data object DismissDialog : HomeScreenUiEvent()
        data object ShowDialog : HomeScreenUiEvent()
    }


    //Action
    sealed class HomeUiAction: UiAction()

}