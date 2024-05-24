package com.example.mvi_pokedex.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.example.mvi_pokedex.domain.useCase.GetPokemonListUsecase
import com.example.mvi_pokedex.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUsecase: GetPokemonListUsecase,
) : BaseViewModel<HomeContract.HomeScreenState, HomeContract.HomeScreenUiEvent, HomeContract.HomeUiAction>() {

    //State of view
    override val initialViewState = HomeContract.HomeScreenState()

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
    override fun reduce(event: HomeContract.HomeScreenUiEvent) {
        when (event) {

            is HomeContract.HomeScreenUiEvent.ShowPokemonFilterItems -> {
                setState { copy(pokemonFilterList = event.pokemonFilterList) }
            }

            is HomeContract.HomeScreenUiEvent.SortPokemonList -> {
                sortPokemonList(event.sortOption)
            }

            is HomeContract.HomeScreenUiEvent.DismissDialog -> TODO()
        }
    }

    private fun sortPokemonList(sortOption: SortOption) {
        setState { copy(isLoading = true, sortSelected = sortOption) }
        when (sortOption) {
            SortOption.Ascending -> {
                setState {
                    copy(
                        isLoading = false,
                        pokemonList = getState().pokemonList.sortedBy { it.id },
                        pokemonFilterList = getState().pokemonFilterList.sortedBy { it.id },
                        )
                }
            }
            SortOption.Descending -> {
                setState {
                    copy(
                        isLoading = false,
                        pokemonList = getState().pokemonList.sortedByDescending { it.id },
                        pokemonFilterList = getState().pokemonFilterList.sortedByDescending { it.id },
                    )
                }
            }
            SortOption.AlphabeticalAZ -> {
                setState {
                    copy(
                        isLoading = false,
                        pokemonList = getState().pokemonList.sortedBy { it.name },
                        pokemonFilterList = getState().pokemonFilterList.sortedBy { it.name },
                    )
                }
            }
            SortOption.AlphabeticalZA -> {
                setState {
                    copy(
                        isLoading = false,
                        pokemonList = getState().pokemonList.sortedByDescending { it.name },
                        pokemonFilterList = getState().pokemonFilterList.sortedByDescending { it.name },
                    )
                }
            }
        }
    }
}