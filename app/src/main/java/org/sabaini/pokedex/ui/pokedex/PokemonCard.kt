package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.ColorUtils

@ExperimentalCoilApi
@Composable
fun PokemonCard(
    pokemon: PokemonUiState,
    onItemClicked: (String) -> Unit,
    onBackgroundColorChange: (PokemonUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val dominantColor = remember { mutableStateOf(pokemon.getBackgroundColor()) }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.dimen_of_5_dp))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp)))
            .size(dimensionResource(R.dimen.dimen_of_150_dp))
            .clickable { onItemClicked(pokemon.name) }
            .background(dominantColor.value),
    ) {
        val painter = rememberImagePainter(data = pokemon.getImageUrl())
        val painterState = painter.state

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.dimen_of_5_dp))
        ) {
            Text(
                text = pokemon.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = pokemon.getFormattedPokemonNumber(),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Row {
            Image(
                painter = painter,
                contentDescription = pokemon.name,
                modifier = Modifier.fillMaxSize()
            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize()
                )
            } else if (painterState is ImagePainter.State.Success && dominantColor.value == Color.Transparent) {
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
    }
}
