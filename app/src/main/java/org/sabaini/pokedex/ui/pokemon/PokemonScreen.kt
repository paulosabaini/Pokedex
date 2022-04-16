package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.pokemon.tabs.PokemonInfoTabs
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants.ZERO
import java.util.*

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun PokemonScreen(pokemonName: String, viewModel: PokemonViewModel) {
    viewModel.fetchPokemonInfo(pokemonName)

    val pagerState = rememberPagerState(ZERO)
    val dominantColor =
        remember { mutableStateOf(viewModel.pokemonInfoUiState.getBackgroundColor()) }

    Column(modifier = Modifier.background(color = dominantColor.value)) {
        Column {
            PokemonNameAndNumber(
                pokemonName = viewModel.pokemonInfoUiState.name,
                pokemonNumber = viewModel.pokemonInfoUiState.getFormattedPokemonNumber()
            )
            PokemonTypes(types = viewModel.pokemonInfoUiState.types)
            PokemonInfoImage(pokemon = viewModel.pokemonInfoUiState, dominantColor = dominantColor)
        }

        PokemonInfoTabs(pagerState = pagerState)
    }
}

@Composable
fun PokemonNameAndNumber(pokemonName: String, pokemonNumber: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
    ) {
        Text(
            text = pokemonName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(R.dimen.dimen_of_35_sp).value.sp,
        )
        Text(
            text = pokemonNumber,
            color = Color.LightGray,
            fontSize = dimensionResource(R.dimen.dimen_of_25_sp).value.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PokemonTypes(types: List<String>) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
    ) {
        types.forEach {
            PokemonType(
                type = it,
                modifier = Modifier.padding(end = dimensionResource(R.dimen.dimen_of_5_dp))
            )
        }
    }
}

@Composable
@ExperimentalCoilApi
fun PokemonInfoImage(
    pokemon: PokemonInfoUiState,
    dominantColor: MutableState<Color>
) {
    val painter = rememberImagePainter(data = pokemon.getImageUrl())
    val painterState = painter.state

    Image(
        painter = painter,
        contentDescription = pokemon.name,
        modifier = Modifier
            .fillMaxWidth()
            .size(dimensionResource(R.dimen.dimen_of_150_dp))
            .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp))
    )
    if (painterState is ImagePainter.State.Loading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(dimensionResource(R.dimen.dimen_of_150_dp))
                .padding(bottom = dimensionResource(R.dimen.dimen_of_10_dp))
        )
    } else if (painterState is ImagePainter.State.Success && dominantColor.value == Color.Transparent) {
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