package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.runtime.Composable
import org.sabaini.pokedex.ui.pokemon.PokemonInfoUiState

typealias ComposableFun = @Composable (pokemon: PokemonInfoUiState) -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    data object About : TabItem("About", { pokemon -> AboutContent(pokemon) })
    data object BaseStats : TabItem("Base Stats", { pokemon -> BaseStatsContent(pokemon) })
    data object Evolution : TabItem("Evolution", { pokemon -> EvolutionContent(pokemon) })
}
