package org.sabaini.pokedex.ui.pokemon

import androidx.compose.runtime.Composable

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    object About : TabItem("About", { AboutContent() })
    object BaseStats : TabItem("Base Stats", { BaseStatsContent() })
    object Evolution : TabItem("Evolution", { EvolutionContent() })
    object Moves : TabItem("Moves", { MovesContent() })
}
