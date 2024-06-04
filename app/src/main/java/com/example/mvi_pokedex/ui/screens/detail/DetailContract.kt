package com.example.mvi_pokedex.ui.screens.detail

import androidx.compose.runtime.Immutable
import com.example.mvi_pokedex.domain.model.PokemonDetail
import com.example.mvi_pokedex.ui.base.BaseViewContract


class DetailContract: BaseViewContract() {

    @Immutable
    data class DetailScreenState(
        val isLoading: Boolean = true,
        val pokemonDetail: PokemonDetail? = null,
        val showDialog: Boolean = false,
        val errorMsg: String? = "",
        val idPokemon: Int = -1,
    ) : UiState()

    @Immutable
    sealed class DetailScreenUiEvent : UiEvent() {
        data class SetIDPokemon(val idPokemon: Int) : DetailScreenUiEvent()
        data class ShowPokemonDetail(val pokemonDetail: PokemonDetail) : DetailScreenUiEvent()
        data object DismissDialog : DetailScreenUiEvent()
        data object ShowDialog : DetailScreenUiEvent()
    }


    //Action
    sealed class DetailUiAction: UiAction()

}
