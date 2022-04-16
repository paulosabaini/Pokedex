package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.ui.theme.LightGray
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel

@Composable
fun AboutContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(dimensionResource(R.dimen.dimen_of_15_dp))
    ) {
        Text(
            text = viewModel.pokemonInfoUiState.description,
            color = Color.White,
            textAlign = TextAlign.Justify,
            fontSize = dimensionResource(R.dimen.dimen_of_16_sp).value.sp
        )

        HeightAndWeightCard(
            height = viewModel.pokemonInfoUiState.getFormattedHeight(),
            weight = viewModel.pokemonInfoUiState.getFormattedWeight()
        )
    }
}

@Composable
fun HeightAndWeightCard(height: String, weight: String) {
    Card(
        backgroundColor = Color.White,
        elevation = dimensionResource(R.dimen.dimen_of_3_dp),
        modifier = Modifier
            .padding(
                vertical = dimensionResource(R.dimen.dimen_of_10_dp),
                horizontal = dimensionResource(R.dimen.dimen_of_5_dp)
            )
            .fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            HeightAndWeightText(height)
            HeightAndWeightText(weight)
        }
    }
}

@Composable
fun HeightAndWeightText(value: String) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.height), color = LightGray)
        Text(text = value, color = Color.Black)
    }
}

@Preview
@Composable
fun AboutContentPreview() {
    AboutContent(hiltViewModel())
}