package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi

typealias ComposableFun = @Composable () -> Unit

@ExperimentalCoilApi
sealed class TabItem(var title: String, var screen: ComposableFun) {
    object About : TabItem("About", { AboutContent(hiltViewModel()) })
    object BaseStats : TabItem("Base Stats", { BaseStatsContent(hiltViewModel()) })
    object Evolution : TabItem("Evolution", { EvolutionContent(hiltViewModel()) })
}
