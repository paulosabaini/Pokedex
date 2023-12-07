package org.sabaini.pokedex.presentation.pokemon

sealed class PokemonScreenUiState {
    data class Success(val pokemonInfo: PokemonInfoUiState) : PokemonScreenUiState()
    data class Error(val exception: Throwable) : PokemonScreenUiState()
    data object Loading : PokemonScreenUiState()
}
