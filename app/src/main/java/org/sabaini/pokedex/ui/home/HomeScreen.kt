package org.sabaini.pokedex.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import org.sabaini.pokedex.ui.pokedex.PokedexScreen

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun HomeScreen(onClickPokemon: (String) -> Unit = {}) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Tune, contentDescription = "Filter")
            }
        }
    ) {
        PokedexScreen(hiltViewModel(), onClickPokemon)
    }
}