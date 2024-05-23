
package com.example.mvi_pokedex.domain.model

import com.example.mvi_pokedex.data.network.response.PokemonDetailResponse
import com.example.mvi_pokedex.data.network.response.Types
import com.example.mvi_pokedex.utils.Constants
import java.util.*

data class PokemonDetail(
    val id: String,
    val name: String,
    val imgURL: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense:Int,
    val speed: Int,
    val types: List<String>,
    val weight: Double,
    val height: Double
)

fun PokemonDetailResponse.toModel(): PokemonDetail {
    val id = "N° ${id.toString().padStart(3,'0')}"
    val name = replaceFirstChar(name)
    val imageUrl: String = Constants.IMAGE_POKEMON_URL + id + Constants.IMAGE_POKEMON_FORMAT
    val hp = pokemonDetails[0].statValue
    val attack = pokemonDetails[1].statValue
    val defense = pokemonDetails[2].statValue
    val specialAttack = pokemonDetails[3].statValue
    val specialDefense = pokemonDetails[4].statValue
    val speed = pokemonDetails[5].statValue
    val types = getTypes(types)
    val weight = weight / 10.0
    val height = height / 10.0
    return PokemonDetail(id, name, imageUrl, hp, attack, defense,
        specialAttack, specialDefense, speed, types, weight, height)
}

private fun getTypes(types: List<Types>): List<String> {
    return if (types.size > 1) {
        listOf(replaceFirstChar(types[0].type.name), replaceFirstChar(types[1].type.name))
    } else {
        listOf(replaceFirstChar(types[0].type.name))
    }
}

private fun replaceFirstChar(t: String): String {
    return t.replaceFirstChar {
        // Cada nombre con Mayuscula la primera letra
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}
