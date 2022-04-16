package org.sabaini.pokedex.ui.pokemon.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.pokemon.PokemonType
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants

@ExperimentalCoilApi
@Composable
fun EvolutionContent(viewModel: PokemonViewModel) {
    val evolutionStages =
        listOf(
            stringResource(R.string.evolution_stage_unevolved),
            stringResource(R.string.evolution_stage_first),
            stringResource(R.string.evolution_stage_second),
            stringResource(R.string.evolution_stage_third)
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(dimensionResource(R.dimen.dimen_of_15_dp))
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(viewModel.pokemonInfoUiState.evolutionChain) { index, evolution ->
                EvolutionCard(
                    pokemon = evolution.pokemon,
                    stage = evolutionStages[index],
                    minLevel = evolution.minLevel
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun EvolutionCard(
    pokemon: PokemonInfoUiState,
    stage: String,
    minLevel: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (minLevel != Constants.ZERO) EvolutionArrow(minLevel.toString())
        EvolutionCardPokemon(pokemon, stage)
    }
}

@Composable
fun EvolutionArrow(minLevel: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.level_value, minLevel),
            color = Color.White
        )
        Icon(
            Icons.Filled.ArrowDownward,
            contentDescription = stringResource(R.string.arrow),
            tint = Color.White
        )
    }
}

@Composable
@ExperimentalCoilApi
fun EvolutionCardPokemon(pokemon: PokemonInfoUiState, stage: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp))
    ) {
        EvolutionCardPokemonImage(pokemon)
        Text(text = stage, color = Color.White)
        EvolutionCardPokemonNameAndTypes(pokemon)
    }
}

@Composable
@ExperimentalCoilApi
fun EvolutionCardPokemonImage(pokemon: PokemonInfoUiState) {
    val painter = rememberImagePainter(data = pokemon.getImageUrl())
    val painterState = painter.state
    val dominantColor = remember { mutableStateOf(pokemon.getBackgroundColor()) }
    val vibrantColor = remember { mutableStateOf(pokemon.getBorderColor()) }

    Image(
        painter = painter,
        contentDescription = pokemon.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(dimensionResource(R.dimen.dimen_of_64_dp))
            .clip(CircleShape)
            .border(
                dimensionResource(R.dimen.dimen_of_2_dp),
                vibrantColor.value,
                CircleShape
            )
            .background(color = dominantColor.value)
    )

    if (painterState is ImagePainter.State.Loading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(dimensionResource(R.dimen.dimen_of_64_dp))
                .clip(CircleShape)
                .border(
                    dimensionResource(R.dimen.dimen_of_2_dp),
                    Color.Transparent,
                    CircleShape
                )
        )
    } else if (painterState is ImagePainter.State.Success && dominantColor.value == Color.Transparent && vibrantColor.value == Color.Transparent) {
        LaunchedEffect(key1 = painter) {
            launch {
                val image = painter.imageLoader.execute(painter.request).drawable
                ColorUtils.calculateDominantColor(image!!) {
                    dominantColor.value = it
                    pokemon.backgroundColor = it
                }
                ColorUtils.calculateVibrantColor(image) {
                    vibrantColor.value = it
                    pokemon.borderColor = it
                }
            }
        }
    }
}

@Composable
fun EvolutionCardPokemonNameAndTypes(pokemon: PokemonInfoUiState) {
    Column(
        modifier = Modifier
            .background(
                Color.White.copy(alpha = Constants.ZERO_POINT_ONE_FLOAT),
                RoundedCornerShape(dimensionResource(R.dimen.dimen_of_5_dp))
            )
            .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = pokemon.name, color = Color.White, fontWeight = FontWeight.Bold)
        Row {
            pokemon.types.forEach { type ->
                PokemonType(
                    type = type,
                    modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_1_dp))
                )
            }
        }
    }
}