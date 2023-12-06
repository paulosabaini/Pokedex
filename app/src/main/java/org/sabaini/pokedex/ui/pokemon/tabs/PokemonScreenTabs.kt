package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.util.Constants

@ExperimentalCoilApi
@Composable
fun PokemonInfoTabs(pokemon: PokemonInfoUiState) {
    val tabs = listOf(TabItem.About, TabItem.BaseStats, TabItem.Evolution)
    val pagerState = rememberPagerState(
        initialPage = Constants.ZERO,
        initialPageOffsetFraction = 0f,
    ) {
        tabs.size
    }

    Column(
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = dimensionResource(R.dimen.dimen_of_30_dp),
                topEnd = dimensionResource(R.dimen.dimen_of_30_dp),
            ),
        ),
    ) {
        TabsOptions(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState, pokemon = pokemon)
    }
}

@ExperimentalCoilApi
@Composable
private fun TabsOptions(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = Black,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(
                    currentTabPosition = tabPositions[pagerState.currentPage],
                ),
                color = Color.White,
            )
        },
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = {
                    Text(
                        text = tab.title,
                        fontSize = dimensionResource(R.dimen.dimen_of_13_sp).value.sp,
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun TabsContent(tabs: List<TabItem>, pagerState: PagerState, pokemon: PokemonInfoUiState) {
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen(pokemon)
    }
}
