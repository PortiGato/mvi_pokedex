package com.example.mvi_pokedex.data.network.service

import com.example.mvi_pokedex.data.network.api.PokeApi
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.utils.Constants.FIRST_POKEMON
import com.example.mvi_pokedex.utils.Constants.NUM_POKEMONS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonListService @Inject constructor(private val api: PokeApi) {
    suspend fun getPokemonList(offset: Int = FIRST_POKEMON,limit: Int = NUM_POKEMONS): Flow<PokemonListResponse> {
        return flow {
            val response = api.getPokemonList(offset,limit)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}