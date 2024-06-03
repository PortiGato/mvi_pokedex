package com.example.mvi_pokedex.data.network.service

import com.example.mvi_pokedex.data.network.api.PokeApi
import com.example.mvi_pokedex.data.network.response.PokemonDetailResponse
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonDetailService @Inject constructor(private val api: PokeApi) {
    suspend fun getPokemonDetail(id: Int): Flow<PokemonDetailResponse> {
        return flow {
            val response = api.getPokemonDetail(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}