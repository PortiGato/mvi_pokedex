package com.example.mvi_pokedex.data.network.response

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: Any? = null,
    @SerializedName("results")
    val results: List<PokemonResponse>? = null
)