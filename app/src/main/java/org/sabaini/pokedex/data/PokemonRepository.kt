package org.sabaini.pokedex.data

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.sabaini.pokedex.data.local.PokemonInfoEvolutionLocalModel
import org.sabaini.pokedex.data.local.PokemonInfoLocalModel
import org.sabaini.pokedex.data.local.PokemonLocalDataSource
import org.sabaini.pokedex.data.local.asUiState
import org.sabaini.pokedex.data.remote.PokemonInfoApiModel
import org.sabaini.pokedex.data.remote.PokemonRemoteDataSource
import org.sabaini.pokedex.data.remote.asLocalModel
import org.sabaini.pokedex.data.remote.asStatLocalModel
import org.sabaini.pokedex.presentation.pokemon.PokemonInfoEvolutionUiState
import org.sabaini.pokedex.presentation.pokemon.PokemonInfoStatUiState
import org.sabaini.pokedex.presentation.pokemon.PokemonInfoUiState
import org.sabaini.pokedex.presentation.pokedex.PokemonUiState
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.ENGLISH
import org.sabaini.pokedex.util.Constants.FIRE_RED
import org.sabaini.pokedex.util.Constants.MINUS_ONE
import org.sabaini.pokedex.util.Constants.NEW_LINE
import org.sabaini.pokedex.util.Constants.ONE
import org.sabaini.pokedex.util.Constants.SLASH
import org.sabaini.pokedex.util.Constants.SPACE
import org.sabaini.pokedex.util.Constants.ZERO
import org.sabaini.pokedex.util.Enums
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val externalScope: CoroutineScope,
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

    suspend fun getPokemonInfo(
        name: String,
        refresh: Boolean = false,
    ): PokemonInfoUiState? {
        return if (refresh) {
            externalScope.async {
                pokemonRemoteDataSource.fetchPokemonInfo(name).also { networkResult ->
                    val localPokemon = saveLocalPokemonInfo(networkResult)
                    insertPokemonInfoEvolutions(localPokemon)
                }
                getLocalPokemonInfo(name)
            }.await()
        } else {
            return getLocalPokemonInfo(name)
        }
    }

    private suspend fun insertPokemonInfoEvolutions(localPokemon: PokemonInfoLocalModel) {
        val pokemonEvolutions =
            pokemonRemoteDataSource.fetchPokemonInfoEvolutions(localPokemon.evolutionChainId)

        val localModelEvolutions = mutableListOf<PokemonInfoEvolutionLocalModel>()
        var chain = pokemonEvolutions.chain
        var evolutionCounter = ZERO

        while (evolutionCounter != MINUS_ONE) {
            val minLevel = chain.evolutionDetails.firstOrNull()?.minLevel ?: ZERO
            val pokemonInfo =
                pokemonRemoteDataSource.fetchPokemonInfo(chain.species.name)
            saveLocalPokemonInfo(pokemonInfo)
            val evolution =
                PokemonInfoEvolutionLocalModel(
                    localPokemon.evolutionChainId.toInt(),
                    pokemonInfo.id,
                    minLevel,
                )
            evolutionCounter++
            localModelEvolutions.add(evolution)

            if (chain.evolvesTo.isNotEmpty()) {
                chain = chain.evolvesTo.first()
            } else {
                evolutionCounter = MINUS_ONE
            }
        }
        pokemonLocalDataSource.insertPokemonInfoEvolution(localModelEvolutions)
    }

    private suspend fun saveLocalPokemonInfo(pokeInfo: PokemonInfoApiModel): PokemonInfoLocalModel {
        val species = pokemonRemoteDataSource.fetchPokemonInfoSpecies(pokeInfo.id.toString())
        val descriptionText =
            species.flavorTextEntries.find { it.version.name == FIRE_RED && it.language.name == ENGLISH }?.flavorText?.replace(
                NEW_LINE,
                SPACE,
            ) ?: BLANK
        val evolutionChainId =
            species.evolutionChain.url.split(SLASH.toRegex()).dropLast(ONE).last()
        val localStats = pokeInfo.stats.asStatLocalModel(pokeInfo.id)
        pokemonLocalDataSource.insertPokemonInfoStat(localStats)
        val pokemon = pokeInfo.asLocalModel()
            .copy(description = descriptionText, evolutionChainId = evolutionChainId)
        pokemonLocalDataSource.insertPokemonInfo(pokemon)
        return pokemon
    }

    private suspend fun getLocalPokemonInfo(name: String): PokemonInfoUiState? {
        val pokemonInfo = pokemonLocalDataSource.fetchPokemonInfoByName(name)
        return pokemonInfo?.let {
            val pokedexPokemon = pokemonLocalDataSource.fetchPokemon(pokemonInfo.name)
            pokemonInfo.asUiState()
                .copy(
                    evolutionChain = getEvolutionChain(pokemonInfo.evolutionChainId.toInt()),
                    baseStats = getBaseStats(pokemonInfo.id),
                    backgroundColor = pokedexPokemon.backgroundColor?.let { color -> Color(color) },
                )
        }
    }

    private suspend fun getEvolutionChain(evolutionChainId: Int): List<PokemonInfoEvolutionUiState> {
        val pokemonEvolutions =
            pokemonLocalDataSource.fetchPokemonInfoEvolution(evolutionChainId)
        return pokemonEvolutions.map {
            val pokemon = pokemonLocalDataSource.fetchPokemonInfo(it.idPokemon)
            PokemonInfoEvolutionUiState(
                pokemon = pokemon.asUiState(),
                minLevel = it.minLevel,
            )
        }
    }

    private suspend fun getBaseStats(id: Int): List<PokemonInfoStatUiState> {
        val pokemonStats = pokemonLocalDataSource.fetchPokemonInfoStat(id)
        return pokemonStats.map {
            val statEnum = Enums.StatType.valueOf(it.name.replace("-", "_").uppercase())
            PokemonInfoStatUiState(
                name = statEnum.stat,
                baseState = it.baseState / 100f,
                color = statEnum.color,
            )
        }
    }

    suspend fun updatePokemonBackgroundColor(name: String, color: Int?) {
        pokemonLocalDataSource.updatePokemonBackgroundColor(name, color)
    }
}
