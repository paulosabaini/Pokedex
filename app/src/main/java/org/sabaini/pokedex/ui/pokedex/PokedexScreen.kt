package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.ui.theme.PokedexTheme
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.SPAN_OVER_SIZED

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
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

    SearchBottomSheetLayout(sheetState = searchModalBottomSheetState)
    GenFilterBottomSheetLayout(sheetState = genModalBottomSheetState)
}

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

@ExperimentalCoilApi
@Composable
fun PokemonCard(
    pokemon: PokemonUiState,
    onItemClicked: (String) -> Unit,
    onBackgroundColorChange: (PokemonUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val dominantColor = remember { mutableStateOf(pokemon.getBackgroundColor()) }

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
                modifier = Modifier.fillMaxSize()
            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize()
                )
            } else if (painterState is ImagePainter.State.Success && dominantColor.value == Color.Transparent) {
                LaunchedEffect(key1 = painter) {
                    launch {
                        val image = painter.imageLoader.execute(painter.request).drawable
                        ColorUtils.calculateDominantColor(image!!) {
                            dominantColor.value = it
                            pokemon.backgroundColor = it
                            onBackgroundColorChange(pokemon)
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
            onItemClicked = {},
            onBackgroundColorChange = { }
        )
    }
}