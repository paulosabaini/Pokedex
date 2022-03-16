package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.pokedex.PokemonType
import org.sabaini.pokedex.ui.viewmodel.PokemonViewModel

@Composable
fun AboutContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .padding(15.dp)
    ) {
        Text(
            text = viewModel.pokemonInfoUiState.description,
            color = Color.White,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )

        Card(
            backgroundColor = Color.White,
            elevation = 3.dp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Height", color = Color(0xFF707070))
                    Text(
                        text = viewModel.pokemonInfoUiState.getFormattedHeight(),
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Weight", color = Color(0xFF707070))
                    Text(
                        text = viewModel.pokemonInfoUiState.getFormattedWeight(),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutContentPreview() {
    AboutContent(hiltViewModel())
}

@Composable
fun BaseStatsContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .padding(15.dp)
    ) {
        StatsBar(statName = "HP", barColor = Color(0xFFFF0000), progressValue = 0.45f)
        StatsBar(statName = "Attack", barColor = Color(0xFFF08030), progressValue = 0.49f)
        StatsBar(statName = "Defense", barColor = Color(0xFFF8D030), progressValue = 0.49f)
        StatsBar(statName = "Sp. Atk", barColor = Color(0xFF6890F0), progressValue = 0.65f)
        StatsBar(statName = "Sp. Def", barColor = Color(0xFF78C850), progressValue = 0.65f)
        StatsBar(statName = "Speed", barColor = Color(0xFFF85888), progressValue = 0.45f)
        StatsBar(statName = "Total", barColor = Color(0xFFA7DB8D), progressValue = 0.318f)
    }
}

@Composable
fun StatsBar(statName: String, barColor: Color, progressValue: Float) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Text(text = statName, color = Color.White)

        Box(contentAlignment = Alignment.Center) {
            LinearProgressIndicator(
                progress = progressValue,
                backgroundColor = Color.DarkGray,
                color = barColor,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(15.dp)
                    .width(280.dp)
            )

            Text(text = "${(progressValue * 100).toInt()}/300", color = Color.White)
        }
    }
}

@Preview
@Composable
fun StatsBarPreview() {
    Surface(color = Color(0xFF2B292C)) {
        StatsBar("EXP", Color.Red, 0.5f)
    }
}

@Preview
@Composable
fun BaseStatsPreview() {
    BaseStatsContent(hiltViewModel())
}

@Composable
fun EvolutionContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Evolution(
            image = painterResource(id = R.drawable.bulbasaur),
            pokemon = "Bulbasaur",
            pokemonColor = Color(0xFF97CBAE),
            borderColor = Color(0xFF682A68),
            stage = "Unevolved",
            pokemonTypes = listOf("Grass", "Poison")
        )

        EvolutionStep(16)

        Evolution(
            image = painterResource(id = R.drawable.bulbasaur),
            pokemon = "Ivysaur",
            pokemonColor = Color(0xFF97CBAE),
            borderColor = Color(0xFF682A68),
            stage = "First evolution",
            pokemonTypes = listOf("Grass", "Poison")
        )

        EvolutionStep(level = 32)

        Evolution(
            image = painterResource(id = R.drawable.bulbasaur),
            pokemon = "Venusaur",
            pokemonColor = Color(0xFF97CBAE),
            borderColor = Color(0xFF682A68),
            stage = "Second evolution",
            pokemonTypes = listOf("Grass", "Poison")
        )
    }
}

@Composable
fun Evolution(
    image: Painter,
    pokemon: String,
    borderColor: Color,
    pokemonColor: Color,
    stage: String,
    pokemonTypes: List<String>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = image,
            contentDescription = pokemon,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, borderColor, CircleShape)
                .background(color = pokemonColor)
        )

        Text(text = stage, color = Color.White)

        Column(
            modifier = Modifier.background(
                Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(5.dp)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = pokemon, color = Color.White, fontWeight = FontWeight.Bold)
            Row {
                pokemonTypes.forEach { type ->
                    PokemonType(
                        type = type,
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EvolutionPreview() {
    Surface(color = Color(0xFF2B292C)) {
        Evolution(
            image = painterResource(id = R.drawable.bulbasaur),
            pokemon = "Bulbasaur",
            pokemonColor = Color(0xFF97CBAE),
            borderColor = Color(0xFF682A68),
            stage = "Unevolved",
            pokemonTypes = listOf("Grass", "Poison")
        )
    }
}

@Composable
fun EvolutionStep(level: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Level $level", color = Color.White)
        Icon(Icons.Filled.ArrowDownward, contentDescription = "Arrow", tint = Color.White)
    }
}

@Preview
@Composable
fun EvolutionContentPreview() {
    EvolutionContent(hiltViewModel())
}

@Composable
fun MovesContent(viewModel: PokemonViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Moves",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview
@Composable
fun MovesContentPreview() {
    MovesContent(hiltViewModel())
}