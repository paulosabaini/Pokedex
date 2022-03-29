package org.sabaini.pokedex.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.google.accompanist.pager.ExperimentalPagerApi
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.Destinations.POKEDEX_SCREEN
import org.sabaini.pokedex.ui.Destinations.POKEMON_SCREEN
import org.sabaini.pokedex.ui.Destinations.POKEMON_SCREEN_ARGUMENT
import org.sabaini.pokedex.ui.home.HomeScreen
import org.sabaini.pokedex.ui.pokemon.PokemonScreen
import org.sabaini.pokedex.ui.theme.PokedexTheme
import org.sabaini.pokedex.util.Constants.BLANK

@Composable
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
fun PokedexApp() {
    PokedexTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar {
                    Text(
                        text = stringResource(R.string.pokedex),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_5_dp))
                    )
                }
            }
        ) {
            NavHost(navController = navController, startDestination = POKEDEX_SCREEN) {
                composable(POKEDEX_SCREEN) {
                    HomeScreen(onClickPokemon = { name ->
                        navController.navigate(
                            "$POKEMON_SCREEN/$name"
                        )
                    })
                }
                composable(
                    route = "$POKEMON_SCREEN/{$POKEMON_SCREEN_ARGUMENT}",
                    arguments = listOf(
                        navArgument(POKEMON_SCREEN_ARGUMENT) {
                            type = NavType.StringType
                        }
                    )
                ) { entry ->
                    val pokemonName = entry.arguments?.getString(POKEMON_SCREEN_ARGUMENT)
                    PokemonScreen(pokemonName = pokemonName ?: BLANK, hiltViewModel())
                }
            }
        }
    }
}

object Destinations {
    const val POKEDEX_SCREEN = "pokedex"
    const val POKEMON_SCREEN = "pokemon"
    const val POKEMON_SCREEN_ARGUMENT = "pokemonName"
}