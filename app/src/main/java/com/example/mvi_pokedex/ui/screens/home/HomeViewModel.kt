package com.example.mvi_pokedex.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.example.mvi_pokedex.domain.useCase.GetPokemonListUsecase
import com.example.mvi_pokedex.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUsecase: GetPokemonListUsecase,
) : BaseViewModel<HomeContract.HomeScreenState, HomeContract.HomeScreenUiEvent, HomeContract.HomeUiAction>() {

    //State of view
    override val initialViewState = HomeContract.HomeScreenState()

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
            is HomeContract.HomeScreenUiEvent.FetchPokemonList -> {
                fetchPokemon()
            }
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

    private fun fetchPokemon() {
        viewModelScope.launch() {
            setState { copy(isLoading = true) }
            getPokemonListUsecase()
                .catch { exception ->
                    setState { copy(errorMsg = exception.message) }
                }
                .collect { pokemons ->
                    setState { copy(pokemonList = pokemons, isLoading = false) }
                }
        }
    }
}