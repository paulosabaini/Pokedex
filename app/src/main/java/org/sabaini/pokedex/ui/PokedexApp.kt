package org.sabaini.pokedex.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.Destinations.POKEDEX_SCREEN
import org.sabaini.pokedex.ui.Destinations.POKEMON_SCREEN
import org.sabaini.pokedex.ui.Destinations.POKEMON_SCREEN_ARGUMENT
import org.sabaini.pokedex.ui.pokedex.PokedexScreen
import org.sabaini.pokedex.ui.pokemon.PokemonScreen
import org.sabaini.pokedex.ui.theme.PokedexTheme
import org.sabaini.pokedex.util.Constants.BLANK

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
fun PokedexApp() {
    PokedexTheme {
        val navController = rememberNavController()
        val upAvailable = remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                PokedexTopBar(upAvailable) {
                    navController.navigateUp()
                }
            },
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = POKEDEX_SCREEN,
            ) {
                composable(POKEDEX_SCREEN) {
                    upAvailable.value = false
                    PokedexScreen(viewModel = hiltViewModel(), onClickPokemon = { name ->
                        navController.navigate(
                            "$POKEMON_SCREEN/$name",
                        )
                    })
                }
                composable(
                    route = "$POKEMON_SCREEN/{$POKEMON_SCREEN_ARGUMENT}",
                    arguments = listOf(
                        navArgument(POKEMON_SCREEN_ARGUMENT) {
                            type = NavType.StringType
                        },
                    ),
                ) { entry ->
                    upAvailable.value = true
                    val pokemonName = entry.arguments?.getString(POKEMON_SCREEN_ARGUMENT)
                    PokemonScreen(pokemonName = pokemonName ?: BLANK, hiltViewModel())
                }
            }
        }
    }
}

@Composable
fun PokedexTopBar(upAvailable: MutableState<Boolean>, onNavigateUp: () -> Unit) {
    if (upAvailable.value) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.pokedex),
                    fontWeight = FontWeight.Bold,
                )
            },
            navigationIcon = {
                IconButton(onClick = { onNavigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.pokedex),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_5_dp)),
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

object Destinations {
    const val POKEDEX_SCREEN = "pokedex"
    const val POKEMON_SCREEN = "pokemon"
    const val POKEMON_SCREEN_ARGUMENT = "pokemonName"
}
