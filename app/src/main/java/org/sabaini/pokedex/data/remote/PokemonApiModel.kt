package org.sabaini.pokedex.data.remote

import org.sabaini.pokedex.data.local.PokemonLocalModel

data class PokemonListApiModel(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonApiModel>
)

data class PokemonApiModel(
    val name: String,
    val url: String
)

fun List<PokemonApiModel>.asLocalModel(): List<PokemonLocalModel> {
    return map {
        PokemonLocalModel(
            name = it.name,
            url = it.url
        )
    }
}