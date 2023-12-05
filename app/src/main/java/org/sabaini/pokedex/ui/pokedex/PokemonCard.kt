package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.ui.theme.LightGray
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.toTitleCase

@ExperimentalCoilApi
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: PokemonUiState,
    onCalculateDominantColor: (Color) -> Unit,
    onItemClicked: (String) -> Unit,
) {
    var containerColor by remember {
        mutableStateOf(pokemon.backgroundColor ?: LightGray)
    }
    Card(
        onClick = { onItemClicked(pokemon.name) },
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
            .size(dimensionResource(R.dimen.dimen_of_150_dp)),
    ) {
        PokemonCardHeader(pokemon)
        PokemonCardImage(
            pokemonName = pokemon.name,
            pokemonImageUrl = pokemon.getImageUrl(),
            onCalculateDominantColor = {
                containerColor = it
                onCalculateDominantColor(it)
            },
        )
    }
}

@Composable
private fun PokemonCardHeader(pokemon: PokemonUiState) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
    ) {
        Text(
            text = pokemon.name.toTitleCase(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = pokemon.getFormattedPokemonNumber(),
            color = Color.LightGray,
        )
    }
}

@Composable
@ExperimentalCoilApi
private fun PokemonCardImage(
    pokemonName: String,
    pokemonImageUrl: String,
    onCalculateDominantColor: (Color) -> Unit,
) {
    var showLoading by remember { mutableStateOf(true) }

    AsyncImage(
        model = pokemonImageUrl,
        contentDescription = pokemonName,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(R.dimen.dimen_of_30_dp)),
        onState = { painterState ->
            when (painterState) {
                is AsyncImagePainter.State.Loading -> showLoading = true
                is AsyncImagePainter.State.Empty -> showLoading = true
                is AsyncImagePainter.State.Error -> showLoading = true
                is AsyncImagePainter.State.Success -> {
                    ColorUtils.calculateDominantColor(painterState.result.drawable) {
                        onCalculateDominantColor(it)
                    }
                }
            }
        },
    )

    if (showLoading) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensionResource(R.dimen.dimen_of_30_dp)),
        )
    }
}
