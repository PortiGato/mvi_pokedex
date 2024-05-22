package com.example.mvi_pokedex.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.example.mvi_pokedex.R
import com.example.mvi_pokedex.domain.model.CardItem
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

    val items = listOf(
        CardItem("Title 1", "Subtitle 1", R.drawable.ic_launcher_background),
        CardItem("Title 2", "Subtitle 2", R.drawable.ic_launcher_background),
        CardItem("Title 3", "Subtitle 3", R.drawable.ic_launcher_background)
    )

    //init function, get data from local database and sync with remote database
    init {
        //setState { copy(cardItem= items) }
        viewModelScope.launch() {
            val resultPokemonList = getPokemonListUsecase()
            if (resultPokemonList.isSuccess) {
                setState {
                    copy(
                        cardItem = resultPokemonList.getOrNull().orEmpty(),
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
            is HomeContract.HomeScreenUiEvent.ShowDialog -> {
                setState { copy(isShowAddDialog = event.show) }
            }

            is HomeContract.HomeScreenUiEvent.ShowSyncLoading -> {
                setState { copy(syncLoading = event.show) }
            }

            is HomeContract.HomeScreenUiEvent.DismissDialog -> {
                setState { copy(isShowAddDialog = false) }
            }

            is HomeContract.HomeScreenUiEvent.ShowCardItems -> {
                setState { copy(cardItem = event.cardItem) }
            }

        }
    }
}