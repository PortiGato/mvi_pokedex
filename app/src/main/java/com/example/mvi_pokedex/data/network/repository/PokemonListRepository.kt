package com.example.mvi_pokedex.data.network.repository

import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.data.network.service.PokemonListService
import com.example.mvi_pokedex.utils.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class PokemonListRepository @Inject constructor(
    var pokemonListService: PokemonListService
) {

    ////ONLINE SERVICE

    suspend fun getPokemonList(
        limit: Int = Constants.NUM_POKEMONS, offset: Int = Constants.FIRST_POKEMON
    ): Result<PokemonListResponse> {

        val response = pokemonListService.getPokemonList(limit,offset)
        return if (response != null) {
            Result.success(response)
        } else {
            Result.failure(Exception("Error en la solicitud"))
        }
    }

}