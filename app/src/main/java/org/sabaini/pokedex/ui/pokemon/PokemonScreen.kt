package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants.ZERO
import org.sabaini.pokedex.util.Enums

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun PokemonScreen(pokemonName: String, viewModel: PokemonViewModel) {
    viewModel.fetchPokemonInfo(pokemonName)

    val tabs = listOf(TabItem.About, TabItem.BaseStats, TabItem.Evolution, TabItem.Moves)
    val pagerState = rememberPagerState(ZERO)
    val dominantColor = remember { mutableStateOf(Color.Transparent) }

    Column(modifier = Modifier.background(color = dominantColor.value)) {
        Column {
            val painter = rememberImagePainter(data = viewModel.pokemonInfoUiState.getImageUrl())
            val painterState = painter.state

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.dimen_of_5_dp))
            ) {
                Text(
                    text = viewModel.pokemonInfoUiState.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = dimensionResource(R.dimen.dimen_of_35_sp).value.sp,
                )
                Text(
                    text = viewModel.pokemonInfoUiState.getFormatedPokemonNumber(),
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = dimensionResource(R.dimen.dimen_of_25_sp).value.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.dimen_of_5_dp),
                    top = dimensionResource(R.dimen.dimen_of_5_dp)
                )
            ) {
                viewModel.pokemonInfoUiState.types.forEach {
                    PokemonType(
                        type = it,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.dimen_of_5_dp))
                    )
                }
            }
            Image(
                painter = painter,
                contentDescription = viewModel.pokemonInfoUiState.name,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.dimen_of_150_dp))
                    .align(CenterHorizontally)
                    .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp))
            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.dimen_of_150_dp))
                        .align(CenterHorizontally)
                        .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp))
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

        Column(
            modifier = Modifier.clip(
                RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.dimen_of_30_dp),
                    topEnd = dimensionResource(R.dimen.dimen_of_30_dp)
                )
            )
        ) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Black,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = {
                    Text(
                        text = tab.title,
                        fontSize = dimensionResource(R.dimen.dimen_of_13_sp).value.sp
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}

@Composable
fun PokemonType(
    type: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                Enums.PokemonTypeColor.valueOf(type.uppercase()).color,
                RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp))
            )
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
    ) {
        Text(
            text = type,
            color = Color.White,
            fontSize = dimensionResource(R.dimen.dimen_of_12_sp).value.sp,
        )
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Preview
@Composable
fun PokemonScreenPreview() {
    PokemonScreen("bulbasaur", hiltViewModel())
}