package com.example.mvi_pokedex.data.network.repository

import com.example.mvi_pokedex.data.network.response.PokemonDetailResponse
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.data.network.service.PokemonDetailService
import com.example.mvi_pokedex.data.network.service.PokemonListService
import com.example.mvi_pokedex.utils.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class PokemonDetailRepository @Inject constructor(
    private var pokemonDetailService: PokemonDetailService
) {

    //ONLINE SERVICE

    suspend fun getPokemonDetail(
        id: Int
    ): Result<PokemonDetailResponse> {
        val response = pokemonDetailService.getPokemonDetail(id)
        return if (response != null) {
            Result.success(response)
        } else {
            Result.failure(Exception("Error en la solicitud"))
        }
    }

}