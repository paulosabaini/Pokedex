package org.sabaini.pokedex.ui.state

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import org.sabaini.pokedex.util.Constants.BLANK

data class PokemonUiState(
    var page: Int,
    val name: String,
    val url: String
) {
    private fun getPokemonNumber(): String {
        return url.split("/".toRegex()).dropLast(1).last()
    }

    fun getFormatedPokemonNumber(): String {
        return "#" + getPokemonNumber().padStart(3, '0')
    }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getPokemonNumber()}.png"
    }
}

data class PokemonInfoUiState(
    val id: Int = 0,
    val name: String = BLANK,
    val types: List<String> = listOf(),
    val description: String = BLANK,
    val height: Int = 0,
    val weight: Int = 0,
    val baseStats: List<PokemonInfoStatUiState> = listOf(),
    val evolutionChain: List<PokemonInfoEvolutionUiState> = listOf()
) {
    fun getFormatedPokemonNumber(): String {
        return "#" + id.toString().padStart(3, '0')
    }

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }

    fun getFormattedHeight(): String = String.format("%.1f m", height.toFloat() / 10)

    fun getFormattedWeight(): String = String.format("%.1f kg", weight.toFloat() / 10)
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