package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.Constants

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    pokemons: LazyPagingItems<PokemonUiState>,
    onClickPokemon: (String) -> Unit,
    onBackgroundColorChange: (String, Color) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = dimensionResource(R.dimen.dimen_of_150_dp)),
    ) {
        items(pokemons.itemCount) { index ->
            pokemons[index]?.let {
                PokemonCard(
                    pokemon = it,
                    onCalculateDominantColor = { color ->
                        onBackgroundColorChange(it.name, color)
                    },
                    onItemClicked = onClickPokemon,
                )
            }
        }
        renderLoading(pokemons)
    }
}

private fun LazyGridScope.renderLoading(lazyPokemonItems: LazyPagingItems<PokemonUiState>) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(Constants.SPAN_OVER_SIZED) }

    lazyPokemonItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item(span = span) { LoadingView(Modifier.fillMaxSize()) }
            }

            loadState.append is LoadState.Loading -> {
                item(span = span) { LoadingItem() }
            }

            loadState.refresh is LoadState.Error -> {
                val e = lazyPokemonItems.loadState.refresh as LoadState.Error
                item(span = span) {
                    ErrorItem(
                        message = e.error.localizedMessage ?: Constants.BLANK,
                        modifier = Modifier.fillMaxSize(),
                        onClickRetry = { retry() },
                    )
                }
            }

            loadState.append is LoadState.Error -> {
                val e = lazyPokemonItems.loadState.append as LoadState.Error
                item(span = span) {
                    ErrorItem(
                        message = e.error.localizedMessage ?: Constants.BLANK,
                        onClickRetry = { retry() },
                    )
                }
            }
        }
    }
}
