package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.pokemon.tabs.PokemonInfoTabs
import org.sabaini.pokedex.ui.theme.LightGray
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.toTitleCase

@ExperimentalCoilApi
@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel,
    onDominantColor: (Color) -> Unit,
) {
    val pokemon = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pokemon.value.backgroundColor) {
        onDominantColor(pokemon.value.backgroundColor ?: LightGray)
    }

    Column(modifier = Modifier.background(color = pokemon.value.backgroundColor ?: LightGray)) {
        PokemonNameAndNumber(
            pokemonName = pokemon.value.name,
            pokemonNumber = pokemon.value.getFormattedPokemonNumber(),
        )
        PokemonTypes(types = pokemon.value.types)
        PokemonInfoImage(
            pokemonName = pokemon.value.name,
            pokemonImageUrl = pokemon.value.getImageUrl(),
        )
        PokemonInfoTabs()
    }
}

@Composable
private fun PokemonNameAndNumber(pokemonName: String, pokemonNumber: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
    ) {
        Text(
            text = pokemonName.toTitleCase(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(R.dimen.dimen_of_35_sp).value.sp,
        )
        Text(
            text = pokemonNumber,
            color = Color.LightGray,
            fontSize = dimensionResource(R.dimen.dimen_of_25_sp).value.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun PokemonTypes(types: List<String>) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
    ) {
        types.forEach {
            PokemonType(
                type = it,
                modifier = Modifier.padding(end = dimensionResource(R.dimen.dimen_of_5_dp)),
            )
        }
    }
}

@Composable
private fun PokemonInfoImage(
    pokemonName: String,
    pokemonImageUrl: String,
) {
    var showLoading by remember { mutableStateOf(true) }

    AsyncImage(
        model = pokemonImageUrl,
        contentDescription = pokemonName,
        modifier = Modifier
            .fillMaxWidth()
            .size(dimensionResource(R.dimen.dimen_of_150_dp))
            .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp)),
        onState = { painterState ->
            showLoading = when (painterState) {
                is AsyncImagePainter.State.Loading -> true
                is AsyncImagePainter.State.Empty -> true
                is AsyncImagePainter.State.Error -> true
                is AsyncImagePainter.State.Success -> false
            }
        },
    )

    if (showLoading) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.dimen_of_64_dp))
                    .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp)),
            )
        }
    }
}
