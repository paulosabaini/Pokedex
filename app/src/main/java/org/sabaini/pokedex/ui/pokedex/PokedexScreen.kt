package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
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
        }
    ) {
        PokemonList(
            pokemons = viewModel.pokeFlow,
            onClickPokemon
        ) { viewModel.updatePokemonColor(it) }
    }

    SearchBottomSheetLayout(sheetState = searchModalBottomSheetState) {
        coroutineScope.launch {
            searchModalBottomSheetState.hide()
        }
    }

    GenFilterBottomSheetLayout(sheetState = genModalBottomSheetState)
}
