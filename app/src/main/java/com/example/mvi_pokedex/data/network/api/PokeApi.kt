package com.example.mvi_pokedex.data.network.api


import com.example.mvi_pokedex.data.network.response.PokemonDetailResponse
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): Response<PokemonDetailResponse>

//    @GET("pokemon/{name}")
//    suspend fun getPokemonDetails(
//        @Path("name") name: String
//    ): Response<PokemonDetailsModel>

//    @GET("pokemon?limit=151")
//    suspend fun getAllPokemonList(): Response<PokemonListResponse>

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