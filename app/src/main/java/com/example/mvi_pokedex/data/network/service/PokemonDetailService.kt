package com.example.mvi_pokedex.data.network.service

import com.example.mvi_pokedex.data.network.api.PokeApi
import com.example.mvi_pokedex.data.network.response.PokemonDetailResponse
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonDetailService @Inject constructor(private val api: PokeApi) {
    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse? {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemonDetail(
                id = id
            )
            response.body()
        }
    }
}