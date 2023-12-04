package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.ColorUtils
import java.util.*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PokemonCard(
    pokemon: PokemonUiState,
    onItemClicked: (String) -> Unit,
    onBackgroundColorChange: (PokemonUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val dominantColor = remember { mutableStateOf(pokemon.getBackgroundColor()) }

    Card(
        onClick = { onItemClicked(pokemon.name) },
        backgroundColor = dominantColor.value,
        modifier = modifier
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
            .size(dimensionResource(R.dimen.dimen_of_150_dp))
    ) {
        PokemonCardHeader(pokemon)
        PokemonCardImage(
            pokemon = pokemon,
            dominantColor = dominantColor,
            onBackgroundColorChange = onBackgroundColorChange
        )
    }
}

@Composable
fun PokemonCardHeader(pokemon: PokemonUiState) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
    ) {
        Text(
            text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = pokemon.getFormattedPokemonNumber(),
            color = Color.LightGray
        )
    }
}

@Composable
@ExperimentalCoilApi
fun PokemonCardImage(
    pokemon: PokemonUiState,
    dominantColor: MutableState<Color>,
    onBackgroundColorChange: (PokemonUiState) -> Unit
) {
    val painter = rememberAsyncImagePainter(model = pokemon.getImageUrl())
    val painterState = painter.state

    Image(
        painter = painter,
        contentDescription = pokemon.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(R.dimen.dimen_of_30_dp))
    )
    if (painterState is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
    } else if (painterState is AsyncImagePainter.State.Success && dominantColor.value == Color.Transparent) {
        LaunchedEffect(key1 = painter) {
            launch {
                val image = painter.imageLoader.execute(painter.request).drawable
                ColorUtils.calculateDominantColor(image!!) {
                    dominantColor.value = it
                    pokemon.backgroundColor = it
                    onBackgroundColorChange(pokemon)
                }
            }
        }
    }
}
