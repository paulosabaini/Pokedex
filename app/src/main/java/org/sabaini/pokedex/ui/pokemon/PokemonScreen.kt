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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import org.sabaini.pokedex.ui.pokedex.PokemonType
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.ColorUtils

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun PokemonScreen(pokemonName: String, viewModel: PokemonViewModel) {

    viewModel.fetchPokemonInfo(pokemonName)

    val tabs = listOf(TabItem.About, TabItem.BaseStats, TabItem.Evolution, TabItem.Moves)
    val pagerState = rememberPagerState(0)
    val dominantColor = remember { mutableStateOf(Color.Transparent) }

    Column(modifier = Modifier.background(color = dominantColor.value)) {
        Column {
            val painter = rememberImagePainter(data = viewModel.pokemonInfoUiState.getImageUrl())
            val painterState = painter.state

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = viewModel.pokemonInfoUiState.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 35.sp,
                )
                Text(
                    text = viewModel.pokemonInfoUiState.getFormatedPokemonNumber(),
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = Modifier.padding(start = 5.dp, top = 5.dp)) {
                viewModel.pokemonInfoUiState.types.forEach {
                    PokemonType(
                        type = it,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            }
            Image(
                painter = painter,
                contentDescription = viewModel.pokemonInfoUiState.name,
                modifier = Modifier
                    .size(150.dp)
                    .align(CenterHorizontally)
                    .padding(bottom = 10.dp)
            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(150.dp)
                        .align(CenterHorizontally)
                        .padding(bottom = 10.dp)
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

        Column(modifier = Modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color(0xFF2B292C),
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(text = tab.title, fontSize = 13.sp) },
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
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Preview
@Composable
fun PokemonScreenPreview() {
    PokemonScreen("bulbasaur", hiltViewModel())
}