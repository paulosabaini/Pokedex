package org.sabaini.pokedex.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import org.sabaini.pokedex.ui.pokedex.PokedexScreen
import org.sabaini.pokedex.ui.state.PokedexUiState
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun HomeScreen(pokedexUiState: PokedexUiState, pokedexViewModel: PokedexViewModel) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Tune, contentDescription = "Filter")
            }
        }
    ) {
        PokedexScreen(pokedexUiState = pokedexUiState, pokedexViewModel = pokedexViewModel)
    }
}