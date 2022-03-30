package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.ui.theme.PokedexTheme
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.SPAN_OVER_SIZED
import org.sabaini.pokedex.util.Constants.TWO_FLOAT
import org.sabaini.pokedex.util.Constants.ZERO_POINT_FIVE_FLOAT

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun PokedexScreen(viewModel: PokedexViewModel, onClickPokemon: (String) -> Unit) {
    PokemonList(pokemons = viewModel.pokeFlow, onClickPokemon)
}

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun PokemonList(pokemons: Flow<PagingData<PokemonUiState>>, onClickPokemon: (String) -> Unit) {

    val lazyPokemonItems = pokemons.collectAsLazyPagingItems()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = dimensionResource(R.dimen.dimen_of_150_dp))
    ) {
        items(lazyPokemonItems.itemCount) { index ->
            lazyPokemonItems[index]?.let {
                PokemonCard(
                    pokemon = it,
                    onItemClicked = onClickPokemon
                )
            }
        }
        renderLoading(lazyPokemonItems)
    }
}

@ExperimentalCoilApi
@Composable
fun PokemonCard(
    pokemon: PokemonUiState,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dominantColor = remember { mutableStateOf(Color.Transparent) }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp)))
            .size(dimensionResource(R.dimen.dimen_of_150_dp))
            .clickable { onItemClicked(pokemon.name) }
            .background(dominantColor.value),
    ) {
        val painter = rememberImagePainter(data = pokemon.getImageUrl())
        val painterState = painter.state

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.dimen_of_5_dp))
        ) {
            Text(
                text = pokemon.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = pokemon.getFormattedPokemonNumber(),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Row {
            Image(
                painter = painter,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(TWO_FLOAT)
            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .scale(ZERO_POINT_FIVE_FLOAT)
                        .fillMaxSize()
                        .weight(TWO_FLOAT)
                )
            } else if (painterState is ImagePainter.State.Success) {
                LaunchedEffect(key1 = painter) {
                    launch {
                        val image = painter.imageLoader.execute(painter.request).drawable
                        ColorUtils.calculateDominantColor(image!!) {
                            dominantColor.value = it
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
private fun LazyGridScope.renderLoading(lazyPokemonItems: LazyPagingItems<PokemonUiState>) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(SPAN_OVER_SIZED) }

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
                        message = e.error.localizedMessage ?: BLANK,
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { retry() }
                    )
                }
            }
            loadState.append is LoadState.Error -> {
                val e = lazyPokemonItems.loadState.append as LoadState.Error
                item(span) {
                    ErrorItem(
                        message = e.error.localizedMessage ?: BLANK,
                        onClickRetry = { retry() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
@ExperimentalCoilApi
fun PreviewPokemonColumn() {
    PokedexTheme {
        PokemonCard(
            pokemon = PokemonUiState(0, "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
            onItemClicked = {}
        )
    }
}