package org.sabaini.pokedex.ui.state

import androidx.compose.ui.graphics.Color
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.NUMBER
import org.sabaini.pokedex.util.Constants.ONE
import org.sabaini.pokedex.util.Constants.SLASH
import org.sabaini.pokedex.util.Constants.TEN
import org.sabaini.pokedex.util.Constants.THREE
import org.sabaini.pokedex.util.Constants.ZERO
import org.sabaini.pokedex.util.Constants.ZERO_CHAR

data class PokemonUiState(
    var page: Int,
    val name: String,
    val url: String
) {
    private fun getPokemonNumber(): String {
        return url.split(SLASH.toRegex()).dropLast(ONE).last()
    }

    fun getFormattedPokemonNumber(): String {
        return NUMBER + getPokemonNumber().padStart(THREE, ZERO_CHAR)
    }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getPokemonNumber()}.png"
    }
}

data class PokemonInfoUiState(
    val id: Int = ZERO,
    val name: String = BLANK,
    val types: List<String> = listOf(),
    val description: String = BLANK,
    val height: Int = ZERO,
    val weight: Int = ZERO,
    val baseStats: List<PokemonInfoStatUiState> = listOf(),
    val evolutionChain: List<PokemonInfoEvolutionUiState> = listOf()
) {
    fun getFormattedPokemonNumber(): String {
        return NUMBER + id.toString().padStart(THREE, ZERO_CHAR)
    }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }

    fun getFormattedHeight(): String = String.format("%.1f m", height.toFloat() / TEN)

    fun getFormattedWeight(): String = String.format("%.1f kg", weight.toFloat() / TEN)
}

data class PokemonInfoStatUiState(
    val name: String,
    val baseState: Float,
    val color: Color
)

data class PokemonInfoEvolutionUiState(
    val pokemon: PokemonInfoUiState,
    val minLevel: Int
)