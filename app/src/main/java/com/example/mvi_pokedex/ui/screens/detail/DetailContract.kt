package com.example.mvi_pokedex.ui.screens.detail

import androidx.compose.runtime.Immutable
import com.example.mvi_pokedex.domain.model.Pokemon
import com.example.mvi_pokedex.ui.base.BaseViewContract


class DetailContract(): BaseViewContract() {
    //State of DetailScreen
    @Immutable
    data class DetailScreenState(
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
    sealed class DetailScreenUiEvent : UiEvent() {
        data class ShowDialog(val show: Boolean) : DetailScreenUiEvent()
        data class ShowSyncLoading(val show: Boolean) : DetailScreenUiEvent()
        data class ShowPokemonFilterItems(val pokemonFilterList: List<Pokemon>) : DetailScreenUiEvent()
        object DismissDialog : DetailScreenUiEvent()
    }


    //Action
    sealed class DetailUiAction: UiAction() {
        data class ShowSnackbar(val msg: String) : DetailUiAction()
    }

}
