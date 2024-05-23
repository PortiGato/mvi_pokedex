package com.example.mvi_pokedex.data.network.response

import com.google.gson.annotations.SerializedName

// https://pokeapi.co/api/v2/pokemon/25/

data class PokemonDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("name") val name: String,
    @SerializedName("stats") val pokemonDetails: List<Stats>,
    @SerializedName("types") val types: List<Types>,
    @SerializedName("weight") val weight: Int
)

data class Stats(
    @SerializedName("base_stat") val statValue: Int,
    @SerializedName("stat") val stat: Stat
)

data class Stat(
    @SerializedName("name") val statName: String
)

data class Types(
    @SerializedName("type") val type: Type
)

data class Type(
    @SerializedName("name") val name: String
)