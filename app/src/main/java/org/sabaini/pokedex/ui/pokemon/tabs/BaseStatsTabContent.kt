package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.Constants.ONE_HUNDRED
import org.sabaini.pokedex.util.Constants.THREE

@Composable
fun BaseStatsContent(viewModel: PokemonViewModel) {
    val pokemon = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(dimensionResource(R.dimen.dimen_of_15_dp)),
    ) {
        pokemon.value.baseStats.forEach {
            StatsBar(statName = it.name, barColor = it.color, progressValue = it.baseState)
        }
    }
}

@Composable
fun StatsBar(statName: String, barColor: Color, progressValue: Float) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.dimen_of_10_dp))
            .fillMaxWidth(),
    ) {
        Text(text = statName, color = Color.White)

        Box(contentAlignment = Alignment.Center) {
            LinearProgressIndicator(
                progress = progressValue / THREE,
                trackColor = Color.DarkGray,
                color = barColor,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp)))
                    .height(dimensionResource(R.dimen.dimen_of_15_dp))
                    .width(dimensionResource(R.dimen.dimen_of_280_dp)),
            )

            Text(
                text = stringResource(
                    R.string.stat_value,
                    (progressValue * ONE_HUNDRED).toInt().toString(),
                ),
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun StatsBarPreview() {
    Surface(color = Black) {
        StatsBar("EXP", Color.Red, 0.5f)
    }
}

@Preview
@Composable
fun BaseStatsPreview() {
    BaseStatsContent(hiltViewModel())
}
