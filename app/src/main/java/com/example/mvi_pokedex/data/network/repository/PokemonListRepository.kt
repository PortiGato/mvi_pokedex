package com.example.mvi_pokedex.data.network.repository

import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.data.network.service.PokemonListService
import com.example.mvi_pokedex.utils.Constants
import com.example.mvi_pokedex.utils.Constants.FIRST_POKEMON
import com.example.mvi_pokedex.utils.Constants.NUM_POKEMONS
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface PokemonListRepository {
    suspend fun getPokemonList(offset: Int,limit: Int): Flow<PokemonListResponse>
}

class PokemonListRepositoryImpl @Inject constructor(
    private val pokemonListService: PokemonListService
) : PokemonListRepository {
    override suspend fun getPokemonList(offset: Int, limit: Int): Flow<PokemonListResponse> {
        return pokemonListService.getPokemonList(offset,limit)
    }
}