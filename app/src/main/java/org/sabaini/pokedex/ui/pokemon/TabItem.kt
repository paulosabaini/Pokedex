package org.sabaini.pokedex.ui.pokemon

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var title: String, var screen: ComposableFun) {
    object About : TabItem("About", { AboutContent(hiltViewModel()) })
    object BaseStats : TabItem("Base Stats", { BaseStatsContent(hiltViewModel()) })
    object Evolution : TabItem("Evolution", { EvolutionContent(hiltViewModel()) })
    object Moves : TabItem("Moves", { MovesContent(hiltViewModel()) })
}
