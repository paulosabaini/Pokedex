package org.sabaini.pokedex.ui.state

import androidx.compose.ui.graphics.Color
import org.sabaini.pokedex.util.Constants.ONE
import org.sabaini.pokedex.util.Constants.SLASH
import org.sabaini.pokedex.util.PokemonUtils.getDisplayPokemonNumber
import org.sabaini.pokedex.util.PokemonUtils.getPokemonImageUrl

data class PokemonUiState(
    var page: Int,
    val name: String,
    val url: String,
    var backgroundColor: Color? = null,
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
