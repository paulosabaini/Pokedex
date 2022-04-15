package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.flow.Flow
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.Constants

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun PokemonList(
    pokemons: Flow<PagingData<PokemonUiState>>,
    onClickPokemon: (String) -> Unit,
    onBackgroundColorChange: (PokemonUiState) -> Unit
) {

    val lazyPokemonItems = pokemons.collectAsLazyPagingItems()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = dimensionResource(R.dimen.dimen_of_150_dp))
    ) {
        items(lazyPokemonItems.itemCount) { index ->
            lazyPokemonItems[index]?.let {
                PokemonCard(
                    pokemon = it,
                    onItemClicked = onClickPokemon,
                    onBackgroundColorChange = onBackgroundColorChange
                )
            }
        }
        renderLoading(lazyPokemonItems)
    }
}

@ExperimentalFoundationApi
private fun LazyGridScope.renderLoading(lazyPokemonItems: LazyPagingItems<PokemonUiState>) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(Constants.SPAN_OVER_SIZED) }

    lazyPokemonItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item(span) { LoadingView(Modifier.fillParentMaxSize()) }
            }
            loadState.append is LoadState.Loading -> {
                item(span) { LoadingItem() }
            }
            loadState.refresh is LoadState.Error -> {
                val e = lazyPokemonItems.loadState.refresh as LoadState.Error
                item(span) {
                    ErrorItem(
                        message = e.error.localizedMessage ?: Constants.BLANK,
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { retry() }
                    )
                }
            }
            loadState.append is LoadState.Error -> {
                val e = lazyPokemonItems.loadState.append as LoadState.Error
                item(span) {
                    ErrorItem(
                        message = e.error.localizedMessage ?: Constants.BLANK,
                        onClickRetry = { retry() }
                    )
                }
            }
        }
    }
}