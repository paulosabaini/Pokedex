package org.sabaini.pokedex.ui.pokemon

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.ui.theme.LightGray
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel
import org.sabaini.pokedex.util.ColorUtils
import org.sabaini.pokedex.util.Constants.ONE_HUNDRED
import org.sabaini.pokedex.util.Constants.THREE
import org.sabaini.pokedex.util.Constants.ZERO
import org.sabaini.pokedex.util.Constants.ZERO_POINT_ONE_FLOAT

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

        Card(
            backgroundColor = Color.White,
            elevation = dimensionResource(R.dimen.dimen_of_3_dp),
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.dimen_of_10_dp),
                    bottom = dimensionResource(R.dimen.dimen_of_10_dp),
                    start = dimensionResource(R.dimen.dimen_of_5_dp),
                    end = dimensionResource(R.dimen.dimen_of_5_dp)
                )
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.height), color = LightGray)
                    Text(
                        text = viewModel.pokemonInfoUiState.getFormattedHeight(),
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.weight), color = LightGray)
                    Text(
                        text = viewModel.pokemonInfoUiState.getFormattedWeight(),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun BaseStatsContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(dimensionResource(R.dimen.dimen_of_15_dp))
    ) {
        viewModel.pokemonInfoUiState.baseStats.forEach {
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
            .fillMaxWidth()
    ) {
        Text(text = statName, color = Color.White)

        Box(contentAlignment = Alignment.Center) {
            LinearProgressIndicator(
                progress = progressValue / THREE,
                backgroundColor = Color.DarkGray,
                color = barColor,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp)))
                    .height(dimensionResource(R.dimen.dimen_of_15_dp))
                    .width(dimensionResource(R.dimen.dimen_of_280_dp))
            )

            Text(
                text = stringResource(
                    R.string.stat_value,
                    (progressValue * ONE_HUNDRED).toInt().toString()
                ), color = Color.White
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun EvolutionContent(viewModel: PokemonViewModel) {
    val evolutionStage =
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
                Evolution(
                    imageUrl = evolution.pokemon.getImageUrl(),
                    pokemon = evolution.pokemon.name,
                    stage = evolutionStage[index],
                    pokemonTypes = evolution.pokemon.types,
                    minLevel = evolution.minLevel
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun Evolution(
    imageUrl: String,
    pokemon: String,
    stage: String,
    pokemonTypes: List<String>,
    minLevel: Int
) {
    val painter = rememberImagePainter(data = imageUrl)
    val painterState = painter.state
    val dominantColor = remember { mutableStateOf(Color.Transparent) }
    val vibrantColor = remember { mutableStateOf(Color.Transparent) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (minLevel != ZERO) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.level_value, minLevel.toString()),
                    color = Color.White
                )
                Icon(
                    Icons.Filled.ArrowDownward,
                    contentDescription = stringResource(R.string.arrow),
                    tint = Color.White
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp))
        ) {
            Image(
                painter = painter,
                contentDescription = pokemon,
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
            } else if (painterState is ImagePainter.State.Success) {
                LaunchedEffect(key1 = painter) {
                    launch {
                        val image = painter.imageLoader.execute(painter.request).drawable
                        ColorUtils.calculateDominantColor(image!!) {
                            dominantColor.value = it
                        }
                        ColorUtils.calculateVibrantColor(image) {
                            vibrantColor.value = it
                        }
                    }
                }
            }

            Text(text = stage, color = Color.White)

            Column(
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = ZERO_POINT_ONE_FLOAT),
                        RoundedCornerShape(dimensionResource(R.dimen.dimen_of_5_dp))
                    )
                    .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = pokemon, color = Color.White, fontWeight = FontWeight.Bold)
                Row {
                    pokemonTypes.forEach { type ->
                        PokemonType(
                            type = type,
                            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_1_dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovesContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(R.string.coming_soon),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(R.dimen.dimen_of_25_sp).value.sp
        )
    }
}

@Preview
@Composable
fun AboutContentPreview() {
    AboutContent(hiltViewModel())
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

@ExperimentalCoilApi
@Preview
@Composable
fun EvolutionPreview() {
    Surface(color = Black) {
        Evolution(
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            pokemon = "bulbasaur",
            stage = "Unevolved",
            pokemonTypes = listOf("Grass", "Poison"),
            minLevel = 0
        )
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun EvolutionContentPreview() {
    EvolutionContent(hiltViewModel())
}

@Preview
@Composable
fun MovesContentPreview() {
    MovesContent(hiltViewModel())
}