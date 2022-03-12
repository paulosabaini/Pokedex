package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.pokedex.PokemonType

@ExperimentalPagerApi
@Composable
fun PokemonScreen() {
    val tabs = listOf(TabItem.About, TabItem.BaseStats, TabItem.Evolution, TabItem.Moves)
    val pagerState = rememberPagerState(0)
    Column(modifier = Modifier.background(Color(0xFF97CBAE))) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Bulbasaur",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 35.sp,
                )
                Text(
                    text = "#001",
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = Modifier.padding(start = 5.dp, top = 5.dp)) {
                PokemonType(
                    type = "Poison",
                    modifier = Modifier.padding(end = 5.dp)
                )
                PokemonType(type = "Grass")
            }
            Image(
                painter = painterResource(id = R.drawable.bulbasaur),
                contentDescription = "bulbasaur",
                modifier = Modifier
                    .size(150.dp)
                    .align(CenterHorizontally)
                    .padding(bottom = 10.dp)
            )
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
@Preview
@Composable
fun PokemonScreenPreview() {
    PokemonScreen()
}