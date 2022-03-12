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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.ui.theme.PokedexTheme
import org.sabaini.pokedex.ui.viewmodel.PokedexViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.SPAN_OVER_SIZED

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun PokedexScreen(viewModel: PokedexViewModel) {
    PokemonList(pokemons = viewModel.pokeFlow)
}

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun PokemonList(pokemons: Flow<PagingData<PokemonUiState>>) {

    val lazyPokemonItems = pokemons.collectAsLazyPagingItems()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 150.dp)
    ) {
        items(lazyPokemonItems.itemCount) { index ->
            lazyPokemonItems[index]?.let {
                PokemonCard(
                    pokemon = it,
                    onItemClicked = {}
                )
            }
        }
        renderLoading(lazyPokemonItems)
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
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .size(150.dp)
            .clickable { onItemClicked(pokemon.name) }
            .background(dominantColor.value),
    ) {
        val painter = rememberImagePainter(data = pokemon.getImageUrl())
        val painterState = painter.state

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = pokemon.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = pokemon.getFormatedPokemonNumber(),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Row {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .align(Alignment.Bottom)
//                    .padding(start = 5.dp, bottom = 5.dp)
//            ) {
//                PokemonType(
//                    type = "Poison",
//                    modifier = Modifier.padding(bottom = 5.dp)
//                )
//                PokemonType(type = "Grass")
//            }
            Image(
                painter = painter,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .scale(0.5f)
                        .fillMaxSize()
                        .weight(2f)
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

@Composable
fun PokemonType(
    type: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(3.dp)
    ) {
        Text(
            text = type,
            color = Color.White,
            fontSize = 12.sp,
        )
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