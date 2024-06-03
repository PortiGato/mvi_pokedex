package com.example.mvi_pokedex.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.example.mvi_pokedex.domain.useCase.GetPokemonDetailUsecase
import com.example.mvi_pokedex.domain.useCase.GetPokemonListUsecase
import com.example.mvi_pokedex.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUsecase: GetPokemonDetailUsecase,
) : BaseViewModel<DetailContract.DetailScreenState, DetailContract.DetailScreenUiEvent, DetailContract.DetailUiAction>() {

    //State of view
    override val initialViewState = DetailContract.DetailScreenState()

    //init function, get data from local database and sync with remote database
    init {
    }


    //process events
    override fun reduce(event: DetailContract.DetailScreenUiEvent) {
        when (event) {
            is DetailContract.DetailScreenUiEvent.ShowDialog -> {
                setState { copy(isShowAddDialog = event.show) }
            }

            is DetailContract.DetailScreenUiEvent.DismissDialog -> {
                setState { copy(isShowAddDialog = false) }
            }

            is DetailContract.DetailScreenUiEvent.ShowPokemonDetail -> {
                setState { copy(pokemonDetail = event.pokemonDetail) }
            }

            is DetailContract.DetailScreenUiEvent.SetIDPokemon -> {
                setState { copy(idPokemon = event.idPokemon) }
                getPokemonDetail(event.idPokemon)
            }
        }
    }

    private fun getPokemonDetail(idPokemon: Int) {

        viewModelScope.launch() {
            setState { copy(isLoading = true) }
            getPokemonDetailUsecase(idPokemon)
                .catch { exception ->
                    setState { copy(errorMsg = exception.message) }
                }
                .collect { pokemonDetail ->
                    setState { copy(pokemonDetail = pokemonDetail, isLoading = false) }
                }
        }

    }
}