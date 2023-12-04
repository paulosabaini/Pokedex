package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.foundation.layout.Column
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun PokemonInfoTabs(pagerState: PagerState) {
    val tabs = listOf(TabItem.About, TabItem.BaseStats, TabItem.Evolution)

    Column(
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = dimensionResource(R.dimen.dimen_of_30_dp),
                topEnd = dimensionResource(R.dimen.dimen_of_30_dp),
            ),
        ),
    ) {
        TabsOptions(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun TabsOptions(tabs: List<TabItem>, pagerState: PagerState) {
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

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}
