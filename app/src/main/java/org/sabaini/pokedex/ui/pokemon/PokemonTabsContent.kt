package org.sabaini.pokedex.ui.pokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun AboutContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "About",
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
fun AboutContentPreview() {
    AboutContent()
}

@Composable
fun BaseStatsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Base Stats",
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
fun BaseStatsPreview() {
    BaseStatsContent()
}

@Composable
fun EvolutionContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2B292C))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Evolution",
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
fun EvolutionContentPreview() {
    EvolutionContent()
}

@Composable
fun MovesContent() {
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
    MovesContent()
}