package org.sabaini.pokedex.presentation.pokemon

import androidx.compose.ui.graphics.Color
import org.sabaini.pokedex.util.Constants
import org.sabaini.pokedex.util.PokemonUtils

data class PokemonInfoUiState(
    val id: Int = Constants.ZERO,
    val name: String = Constants.BLANK,
    val types: List<String> = listOf(),
    val description: String = Constants.BLANK,
    val height: Int = Constants.ZERO,
    val weight: Int = Constants.ZERO,
    var backgroundColor: Color? = null,
    var borderColor: Color? = null,
    val baseStats: List<PokemonInfoStatUiState> = listOf(),
    val evolutionChain: List<PokemonInfoEvolutionUiState> = listOf(),
) {
    fun getFormattedPokemonNumber(): String {
        return PokemonUtils.getDisplayPokemonNumber(id.toString())
    }

    fun getImageUrl(): String {
        return PokemonUtils.getPokemonImageUrl(id.toString())
    }

    fun getFormattedHeight(): String = String.format("%.1f m", height.toFloat() / Constants.TEN)

    fun getFormattedWeight(): String = String.format("%.1f kg", weight.toFloat() / Constants.TEN)

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
    val color: Color,
)

data class PokemonInfoEvolutionUiState(
    val pokemon: PokemonInfoUiState,
    val minLevel: Int,
)
