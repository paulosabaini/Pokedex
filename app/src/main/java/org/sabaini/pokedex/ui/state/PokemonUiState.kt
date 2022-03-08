package org.sabaini.pokedex.ui.state

data class PokemonUiState(
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