package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.PokedexTheme

@Composable
@ExperimentalFoundationApi
fun PokedexScreen() {
    val pokemons = listOf(
        "bulbasaur",
        "charmander",
        "squirtle",
        "pikachu",
        "chikorita",
        "cyndaquil",
        "totodile",
        "ivysaur",
        "venusaur",
        "charmeleon",
        "charizard",
        "ditto",
        "farfetch'd",
        "psyduck",
        "magikarp"
    )

    PokemonList(pokemons = pokemons)
}

@Composable
@ExperimentalFoundationApi
fun PokemonList(pokemons: List<String>) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 150.dp)
    ) {
        items(items = pokemons) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                onItemClicked = {}
            )
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: String,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .size(150.dp)
            .clickable { onItemClicked(pokemon) }
            .background(Color.Blue)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = pokemon,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "#001",
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Bottom)
                    .padding(start = 5.dp, bottom = 5.dp)
            ) {
                PokemonType(
                    type = "Poison",
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                PokemonType(type = "Grass")
            }
            Image(
                painter = painterResource(id = R.drawable.bulbasaur),
                contentDescription = "bulbasaur",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
            )
        }
    }
}

@Composable
fun PokemonType(
    type: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(3.dp)
    ) {
        Text(
            text = type,
            color = Color.White,
            fontSize = 12.sp,
        )
    }
}

@Preview
@Composable
fun PreviewPokemonColumn() {
    PokedexTheme {
        PokemonCard(
            pokemon = "Bulbasaur",
            onItemClicked = {}
        )
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewPokedexScreen() {
    PokedexTheme {
        PokedexScreen()
    }
}