package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
fun PokedexScreen(viewModel: PokedexViewModel, onClickPokemon: (String) -> Unit) {
    var showSearchBottomSheet by remember { mutableStateOf(false) }
    var showGenBottomSheet by remember { mutableStateOf(false) }
    val pokemons = viewModel.pokeFlow.collectAsLazyPagingItems()

    Scaffold(
        floatingActionButton = {
            FabSpeedDial {
                when (it) {
                    R.id.menu_item_search -> {
                        showSearchBottomSheet = true
                    }

                    R.id.menu_item_gen -> {
                        showGenBottomSheet = true
                    }
                }
            }
        },
    ) { contentPadding ->
        PokemonList(
            modifier = Modifier.consumeWindowInsets(contentPadding).padding(contentPadding),
            pokemons = pokemons,
            onBackgroundColorChange = { pokemon, color ->
                viewModel.updatePokemonColor(pokemon, color.toArgb())
            },
            onClickPokemon = onClickPokemon,
        )

        if (showSearchBottomSheet) {
            SearchBottomSheetLayout(
                onDismiss = {
                    showSearchBottomSheet = false
                },
                onSearch = {},
            )
        }

        if (showGenBottomSheet) {
            GenFilterBottomSheetLayout {
                showGenBottomSheet = false
            }
        }
    }
}
