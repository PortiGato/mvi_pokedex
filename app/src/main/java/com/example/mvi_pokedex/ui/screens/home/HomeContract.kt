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
        val isShowAddDialog: Boolean = false,
        val dialogMsg: String = "",
        var searchQuery: String = "",
        val syncLoading: Boolean = false,
    ) : UiState() {
        override fun toString(): String {
            return "isLoading: $isLoading, data.size: ${pokemonList.size}, isShowAddDialog: $isShowAddDialog"
        }
    }


    // Posible Events (or intents)
    @Immutable
    sealed class HomeScreenUiEvent : UiEvent() {
        data class ShowDialog(val show: Boolean) : HomeScreenUiEvent()
        data class ShowSyncLoading(val show: Boolean) : HomeScreenUiEvent()
        data class ShowPokemonFilterItems(val pokemonFilterList: List<Pokemon>) : HomeScreenUiEvent()
        object DismissDialog : HomeScreenUiEvent()
    }


    //Action
    sealed class HomeUiAction: UiAction() {
        data class ShowSnackbar(val msg: String) : HomeUiAction()
    }

}
