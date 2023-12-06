package org.sabaini.pokedex.util

import org.sabaini.pokedex.util.Constants.ZERO

object PokemonUtils {

    fun getPokemonImageUrl(id: String): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }

    fun getDisplayPokemonNumber(id: String): String {
        if (id == ZERO.toString()) return String()
        return Constants.NUMBER + id.padStart(Constants.THREE, Constants.ZERO_CHAR)
    }
}
