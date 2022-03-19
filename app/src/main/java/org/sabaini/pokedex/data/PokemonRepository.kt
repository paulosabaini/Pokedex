package org.sabaini.pokedex.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.sabaini.pokedex.data.local.PokemonLocalDataSource
import org.sabaini.pokedex.data.local.asUiState
import org.sabaini.pokedex.data.remote.PokemonRemoteDataSource
import org.sabaini.pokedex.data.remote.asLocalModel
import org.sabaini.pokedex.data.remote.asUiSate
import org.sabaini.pokedex.ui.state.PokemonInfoEvolutionUiState
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.Constants.BLANK
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val externalScope: CoroutineScope
) {
    suspend fun getPokemonList(page: Int, refresh: Boolean = false): List<PokemonUiState> {
        return if (refresh) {
            externalScope.async {
                pokemonRemoteDataSource.fetchPokemonList(page = page).also { networkResult ->
                    pokemonLocalDataSource.insertPokemons(networkResult.results.asLocalModel(page = page))
                }
                pokemonLocalDataSource.fetchPokemons(page).asUiState()
            }.await()
        } else {
            return pokemonLocalDataSource.fetchPokemons(page).asUiState()
        }
    }

    suspend fun getPokemonInfo(name: String, refresh: Boolean = false): PokemonInfoUiState {
        val pokemonInfo = pokemonRemoteDataSource.fetchPokemonInfo(name)

        val pokemonDescription =
            pokemonRemoteDataSource.fetchPokemonInfoSpecies(pokemonInfo.id.toString())
        val descriptionText =
            pokemonDescription.flavorTextEntries.find { it.version.name == "firered" && it.language.name == "en" }

        val pokemonEvolutions =
            pokemonRemoteDataSource.fetchPokemonInfoEvolutions(
                pokemonDescription.evolutionChain.url.split(
                    "/".toRegex()
                ).dropLast(1).last()
            )
        val uiStateEvolutions = mutableListOf<PokemonInfoEvolutionUiState>()

        var chain = pokemonEvolutions.chain
        var evolutionCounter = 0

        while (evolutionCounter != -1) {
            val minLevel = chain.evolutionDetails.firstOrNull()?.minLevel ?: 0
            val pokemonInfoEvo = pokemonRemoteDataSource.fetchPokemonInfo(chain.species.name)
            val evolution = PokemonInfoEvolutionUiState(pokemonInfoEvo.asUiSate(), minLevel)
            evolutionCounter++
            uiStateEvolutions.add(evolution)

            if (chain.evolvesTo.isNotEmpty()) {
                chain = chain.evolvesTo.first()
            } else {
                evolutionCounter = -1
            }
        }

        return pokemonInfo.asUiSate().copy(
            description = (descriptionText?.flavorText ?: BLANK).replace("\n", " "),
            evolutionChain = uiStateEvolutions
        )
    }
}