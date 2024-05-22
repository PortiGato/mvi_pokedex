package com.example.mvi_pokedex.domain.useCase

import android.content.Context
import com.example.mvi_pokedex.data.network.repository.PokemonListRepository
import com.example.mvi_pokedex.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class GetPokemonListUsecase @Inject constructor(
    private val pokemonListRepository: PokemonListRepository,
    private val context: Context,
) {
    suspend operator fun invoke(limit: Int = 20, offset: Int = 20) =
        withContext(Dispatchers.IO) {
            try {
                if (Utils.isDeviceOnline(context)) {
                    val response = pokemonListRepository.getPokemonListOnline(limit, offset)
                    if (response.isSuccess) {
                        val listPokemonModel = response.getOrNull()?.results?.map { it.toModel() }.orEmpty()
                        Result.success(listPokemonModel)
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
