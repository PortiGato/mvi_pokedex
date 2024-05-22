package com.example.mvi_pokedex.data.network.api


import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponse>

//    @GET("type/{id}")
//    suspend fun getPokemonListByType(
//        @Path("id") id: String
//    ): PokemonListByTypeResponse
//
//    @GET("pokemon/{id}")
//    suspend fun getPokemonDetails(
//        @Path("id") id: String
//    ): PokemonResponse
//
//    @GET("pokemon-species/{id}")
//    suspend fun getPokemonSpecies(
//        @Path("id") id: String
//    ): PokemonSpeciesResponse
}