package com.example.mvi_pokedex.domain.model

import com.example.mvi_pokedex.utils.Constants.IMAGE_POKEMON_FORMAT
import com.example.mvi_pokedex.utils.Constants.IMAGE_POKEMON_URL

data class Pokemon(
    val name: String,
    val url: String
) {
    val id: Int
        get() {
            val components = url.split("/")
            return components[components.size - 2].toIntOrNull() ?: 0
        }

    val imageUrl: String
        get() = IMAGE_POKEMON_URL + id + IMAGE_POKEMON_FORMAT
}