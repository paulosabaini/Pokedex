package org.sabaini.pokedex.util

object PokemonUtils {

    fun getPokemonImageUrl(id: String): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }

    fun getDisplayPokemonNumber(id: String): String {
        return Constants.NUMBER + id.padStart(Constants.THREE, Constants.ZERO_CHAR)
    }
}