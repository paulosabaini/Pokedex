package org.sabaini.pokedex.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import org.sabaini.pokedex.ui.home.HomeScreen
import org.sabaini.pokedex.ui.theme.PokedexTheme

@Composable
@ExperimentalFoundationApi
fun PokedexApp() {
    PokedexTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    Text(text = "Pokédex")
                }
            }
        ) {
            HomeScreen()
        }
    }
}