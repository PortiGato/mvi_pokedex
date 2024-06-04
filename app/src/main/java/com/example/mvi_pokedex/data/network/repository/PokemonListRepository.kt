package com.example.mvi_pokedex.data.network.repository

import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.data.network.service.PokemonListService
import kotlinx.coroutines.flow.Flow
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