package com.example.mvi_pokedex.domain.useCase

import android.content.Context
import com.example.mvi_pokedex.data.network.repository.PokemonDetailRepository
import com.example.mvi_pokedex.domain.model.PokemonDetail
import com.example.mvi_pokedex.domain.model.toModel
import com.example.mvi_pokedex.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GetPokemonDetailUsecase @Inject constructor(
    private val pokemonDetailRepository: PokemonDetailRepository,
    private val context: Context,
) {
    operator fun invoke(id: Int): Flow<PokemonDetail> = flow {
        try {
            if (Utils.isDeviceOnline(context)) {
                pokemonDetailRepository.getPokemonDetail(id)
                    .collect { response ->
                        emit(response.toModel())
                    }
            } else {
                throw Exception("Sin conexión a internet")
            }
        } catch (ex: HttpException) {
            throw Exception("Error en la solicitud: ${ex.message}")
        }
    }.flowOn(Dispatchers.IO)

    }
