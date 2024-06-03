package com.example.mvi_pokedex.domain.useCase

import android.content.Context
import com.example.mvi_pokedex.data.network.repository.PokemonListRepository
import com.example.mvi_pokedex.domain.model.Pokemon
import com.example.mvi_pokedex.utils.Constants.FIRST_POKEMON
import com.example.mvi_pokedex.utils.Constants.NUM_POKEMONS
import com.example.mvi_pokedex.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GetPokemonListUsecase @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val context: Context,
) {
    operator fun invoke(offset: Int = FIRST_POKEMON, limit: Int = NUM_POKEMONS): Flow<List<Pokemon>> = flow {
        try {
            if (Utils.isDeviceOnline(context)) {
                pokemonListRepository.getPokemonList(offset,limit)
                    .collect { response ->
                        val listPokemon = response.results.map { it.toModel() }
                            .map { pokemonName -> pokemonName.copy(name = pokemonName.name.replaceFirstChar { it.uppercase() }) }
                        emit(listPokemon)
                    }
            } else {
                throw Exception("Sin conexión a internet")
            }
        } catch (ex: HttpException) {
            throw Exception("Error en la solicitud: ${ex.message}")
        }
    }.flowOn(Dispatchers.IO)
}
