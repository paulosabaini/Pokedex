package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
fun PokedexScreen(viewModel: PokedexViewModel, onClickPokemon: (String) -> Unit) {
    val searchModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val genModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FabSpeedDial {
                when (it) {
                    R.id.menu_item_search -> {
                        coroutineScope.launch {
                            searchModalBottomSheetState.show()
                        }
                    }

                    R.id.menu_item_gen -> {
                        coroutineScope.launch {
                            genModalBottomSheetState.show()
                        }
                    }
                }
            }
        },
    ) { contentPadding ->
        PokemonList(
            modifier = Modifier.consumeWindowInsets(contentPadding).padding(contentPadding),
            pokemons = viewModel.pokeFlow,
            onClickPokemon = onClickPokemon,
        ) { viewModel.updatePokemonColor(it) }
    }

    SearchBottomSheetLayout(sheetState = searchModalBottomSheetState) {
        coroutineScope.launch {
            searchModalBottomSheetState.hide()
        }
    }

    GenFilterBottomSheetLayout(sheetState = genModalBottomSheetState)
}
