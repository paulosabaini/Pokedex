package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.pokemon.tabs.PokemonInfoTabs
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.ColorUtils
import java.util.Locale

@ExperimentalCoilApi
@Composable
fun PokemonScreen(pokemonName: String, viewModel: PokemonViewModel) {
    viewModel.fetchPokemonInfo(pokemonName)

    val dominantColor =
        remember { mutableStateOf(viewModel.pokemonInfoUiState.getBackgroundColor()) }

    Column(modifier = Modifier.background(color = dominantColor.value)) {
        Column {
            PokemonNameAndNumber(
                pokemonName = viewModel.pokemonInfoUiState.name,
                pokemonNumber = viewModel.pokemonInfoUiState.getFormattedPokemonNumber(),
            )
            PokemonTypes(types = viewModel.pokemonInfoUiState.types)
            PokemonInfoImage(pokemon = viewModel.pokemonInfoUiState, dominantColor = dominantColor)
        }

        PokemonInfoTabs()
    }
}

@Composable
fun PokemonNameAndNumber(pokemonName: String, pokemonNumber: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
    ) {
        Text(
            text = pokemonName.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(
                        Locale.getDefault(),
                    )
                } else {
                    it.toString()
                }
            },
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
fun PokemonTypes(types: List<String>) {
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
@ExperimentalCoilApi
fun PokemonInfoImage(
    pokemon: PokemonInfoUiState,
    dominantColor: MutableState<Color>,
) {
    val painter = rememberAsyncImagePainter(model = pokemon.getImageUrl())
    val painterState = painter.state

    Image(
        painter = painter,
        contentDescription = pokemon.name,
        modifier = Modifier
            .fillMaxWidth()
            .size(dimensionResource(R.dimen.dimen_of_150_dp))
            .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp)),
    )
    if (painterState is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(dimensionResource(R.dimen.dimen_of_150_dp))
                .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp)),
        )
    } else if (painterState is AsyncImagePainter.State.Success && dominantColor.value == Color.Transparent) {
        LaunchedEffect(key1 = painter) {
            launch {
                val image = painter.imageLoader.execute(painter.request).drawable
                ColorUtils.calculateDominantColor(image!!) {
                    dominantColor.value = it
                    pokemon.backgroundColor = it
                }
            }
        }
    }
}
