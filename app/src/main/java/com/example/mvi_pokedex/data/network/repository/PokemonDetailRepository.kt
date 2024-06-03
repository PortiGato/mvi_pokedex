package com.example.mvi_pokedex.data.network.repository

import com.example.mvi_pokedex.data.network.response.PokemonDetailResponse
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.data.network.service.PokemonDetailService
import com.example.mvi_pokedex.data.network.service.PokemonListService
import com.example.mvi_pokedex.utils.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface PokemonDetailRepository {
    suspend fun getPokemonDetail(id: Int): Flow<PokemonDetailResponse>
}

class PokemonDetailRepositoryImpl @Inject constructor(
    private val pokemonDetailService: PokemonDetailService
) : PokemonDetailRepository {
    override suspend fun getPokemonDetail(id: Int): Flow<PokemonDetailResponse> {
        return pokemonDetailService.getPokemonDetail(id)
    }
}