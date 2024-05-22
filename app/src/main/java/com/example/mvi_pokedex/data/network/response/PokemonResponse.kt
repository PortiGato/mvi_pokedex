package com.example.mvi_pokedex.data.network.response

import com.example.mvi_pokedex.domain.model.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toModel(): Pokemon {
        return Pokemon(
            name = this.name,
            url = this.url
        )
    }
}