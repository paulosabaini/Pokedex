package org.sabaini.pokedex.data

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonResponseItem>
)

data class PokemonResponseItem(
    val name: String,
    val url: String
)