package com.example.mvi_pokedex.data.network.service

import com.example.mvi_pokedex.data.network.api.PokeApi
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonListService @Inject constructor(private val api: PokeApi) {
    suspend fun getPokemonList(limit: Int = Constants.NUM_POKEMONS, offset: Int = Constants.FIRST_POKEMON): PokemonListResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemonList(
                limit = limit,
                offset = offset
            )
            response.body() ?: PokemonListResponse()
        }
    }
}