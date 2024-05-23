package com.example.mvi_pokedex.domain.useCase

import android.content.Context
import com.example.mvi_pokedex.data.network.repository.PokemonDetailRepository
import com.example.mvi_pokedex.data.network.repository.PokemonListRepository
import com.example.mvi_pokedex.domain.model.toModel
import com.example.mvi_pokedex.utils.Constants.FIRST_POKEMON
import com.example.mvi_pokedex.utils.Constants.NUM_POKEMONS
import com.example.mvi_pokedex.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class GetPokemonDetailUsecase @Inject constructor(
    private val pokemonDetailRepository: PokemonDetailRepository,
    private val context: Context,
) {
    suspend operator fun invoke(id: String) =
        withContext(Dispatchers.IO) {
            try {
                if (Utils.isDeviceOnline(context)) {
                    val response = pokemonDetailRepository.getPokemonDetail(id)
                    if (response.isSuccess) {
                        val pokemonDetail = response.getOrNull()?.toModel()
                        Result.success(pokemonDetail)
                    } else
                        Result.failure(Exception("Error en la solicitud " + response.exceptionOrNull()?.message))
                } else {
                    Result.failure(Exception("Sin conexión a internet"))
                }
            } catch (ex: HttpException) {
                Result.failure(Exception("Error en la solicitud " + ex.message))
            }
        }
    }
