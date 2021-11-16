package org.sabaini.pokedex.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.home.HomeScreen
import org.sabaini.pokedex.ui.theme.PokedexTheme

@Composable
@ExperimentalFoundationApi
fun PokedexApp() {
    PokedexTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    Text(
                        text = stringResource(R.string.pokedex),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        ) {
            HomeScreen()
        }
    }
}