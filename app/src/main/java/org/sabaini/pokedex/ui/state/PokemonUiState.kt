package org.sabaini.pokedex.ui.state

import androidx.compose.ui.graphics.Color
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.ONE
import org.sabaini.pokedex.util.Constants.SLASH
import org.sabaini.pokedex.util.Constants.TEN
import org.sabaini.pokedex.util.Constants.ZERO
import org.sabaini.pokedex.util.PokemonUtils.getDisplayPokemonNumber
import org.sabaini.pokedex.util.PokemonUtils.getPokemonImageUrl

data class PokemonUiState(
    var page: Int,
    val name: String,
    val url: String,
    var backgroundColor: Color? = null
) {
    private fun getPokemonNumber(): String {
        return url.split(SLASH.toRegex()).dropLast(ONE).last()
    }

    fun getFormattedPokemonNumber(): String {
        return getDisplayPokemonNumber(getPokemonNumber())
    }

    fun getImageUrl(): String {
        return getPokemonImageUrl(getPokemonNumber())
    }

    fun getBackgroundColor(): Color {
        return backgroundColor ?: Color.Transparent
    }
}

data class PokemonInfoUiState(
    val id: Int = ZERO,
    val name: String = BLANK,
    val types: List<String> = listOf(),
    val description: String = BLANK,
    val height: Int = ZERO,
    val weight: Int = ZERO,
    var backgroundColor: Color? = null,
    var borderColor: Color? = null,
    val baseStats: List<PokemonInfoStatUiState> = listOf(),
    val evolutionChain: List<PokemonInfoEvolutionUiState> = listOf()
) {
    fun getFormattedPokemonNumber(): String {
        return getDisplayPokemonNumber(id.toString())
    }

    fun getImageUrl(): String {
        return getPokemonImageUrl(id.toString())
    }

    fun getFormattedHeight(): String = String.format("%.1f m", height.toFloat() / TEN)

    fun getFormattedWeight(): String = String.format("%.1f kg", weight.toFloat() / TEN)

    fun getBackgroundColor(): Color {
        return backgroundColor ?: Color.Transparent
    }

    fun getBorderColor(): Color {
        return borderColor ?: Color.Transparent
    }
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