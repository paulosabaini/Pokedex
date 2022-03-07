package org.sabaini.pokedex.data.remote

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