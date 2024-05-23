package com.example.mvi_pokedex.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.example.mvi_pokedex.domain.useCase.GetPokemonListUsecase
import com.example.mvi_pokedex.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonListUsecase: GetPokemonListUsecase,
) : BaseViewModel<DetailContract.DetailScreenState, DetailContract.DetailScreenUiEvent, DetailContract.DetailUiAction>() {

    //State of view
    override val initialViewState = DetailContract.DetailScreenState()

    //init function, get data from local database and sync with remote database
    init {

        viewModelScope.launch() {
            setState { copy(isLoading = true) }
            val resultPokemonList = getPokemonListUsecase()
            if (resultPokemonList.isSuccess) {
                setState {
                    copy(
                        pokemonList = resultPokemonList.getOrNull().orEmpty(),
                        isLoading = false
                    )
                }
            } else setState {
                copy(
                    dialogMsg = resultPokemonList.exceptionOrNull().toString(),
                    isLoading = false
                )
            }
        }
    }


    //process events
    override fun reduce(event: DetailContract.DetailScreenUiEvent) {
        when (event) {
            is DetailContract.DetailScreenUiEvent.ShowDialog -> {
                setState { copy(isShowAddDialog = event.show) }
            }

            is DetailContract.DetailScreenUiEvent.ShowSyncLoading -> {
                setState { copy(syncLoading = event.show) }
            }

            is DetailContract.DetailScreenUiEvent.DismissDialog -> {
                setState { copy(isShowAddDialog = false) }
            }

            is DetailContract.DetailScreenUiEvent.ShowPokemonFilterItems -> {
                setState { copy(pokemonFilterList = event.pokemonFilterList) }
            }

        }
    }
}