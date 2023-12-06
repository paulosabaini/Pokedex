package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import org.sabaini.pokedex.ui.state.PokemonInfoUiState

typealias ComposableFun = @Composable (pokemon: PokemonInfoUiState) -> Unit

@ExperimentalCoilApi
sealed class TabItem(var title: String, var screen: ComposableFun) {
    data object About : TabItem("About", { pokemon -> AboutContent(pokemon) })
    data object BaseStats : TabItem("Base Stats", { pokemon -> BaseStatsContent(pokemon) })
    data object Evolution : TabItem("Evolution", { pokemon -> EvolutionContent(pokemon) })
}
