package org.sabaini.pokedex.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.sabaini.pokedex.data.local.*
import org.sabaini.pokedex.data.remote.*
import org.sabaini.pokedex.ui.state.PokemonInfoEvolutionUiState
import org.sabaini.pokedex.ui.state.PokemonInfoStatUiState
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Enums
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

    suspend fun getPokemonInfoTemp(
        name: String,
        id: Int,
        refresh: Boolean = false
    ): PokemonInfoUiState {
        return if (refresh) {
            externalScope.async {
                pokemonRemoteDataSource.fetchPokemonInfo(name).also { networkResult ->
                    val localPokemon = savePokemonInfo(networkResult)

                    val localStats = networkResult.stats.asStatLocalModel(localPokemon.id)
                    pokemonLocalDataSource.insertPokemonInfoStat(localStats)

                    val pokemonEvolutions =
                        pokemonRemoteDataSource.fetchPokemonInfoEvolutions(localPokemon.evolutionChainId)

                    val localModelEvolutions = mutableListOf<PokemonInfoEvolutionLocalModel>()
                    var chain = pokemonEvolutions.chain
                    var evolutionCounter = 0

                    while (evolutionCounter != -1) {
                        val minLevel = chain.evolutionDetails.firstOrNull()?.minLevel ?: 0
                        val pokemonInfo =
                            pokemonRemoteDataSource.fetchPokemonInfo(chain.species.name)
                        savePokemonInfo(pokemonInfo)
                        val evolution =
                            PokemonInfoEvolutionLocalModel(pokemonInfo.id, minLevel)
                        evolutionCounter++
                        localModelEvolutions.add(evolution)

                        if (chain.evolvesTo.isNotEmpty()) {
                            chain = chain.evolvesTo.first()
                        } else {
                            evolutionCounter = -1
                        }
                    }
                    pokemonLocalDataSource.insertPokemonInfoEvolution(localModelEvolutions)
                }
                getLocalPokemonInfo(id)
            }.await()
        } else {
            return getLocalPokemonInfo(id)
        }
    }

    private suspend fun savePokemonInfo(pokeInfo: PokemonInfoApiModel): PokemonInfoLocalModel {
        val species = pokemonRemoteDataSource.fetchPokemonInfoSpecies(pokeInfo.id.toString())
        val descriptionText =
            species.flavorTextEntries.find { it.version.name == "firered" && it.language.name == "en" }?.flavorText?.replace(
                "\n",
                " "
            ) ?: BLANK
        val evolutionChainId = species.evolutionChain.url.split("/".toRegex()).dropLast(1).last()
        val pokemon = pokeInfo.asLocalModel()
            .copy(description = descriptionText, evolutionChainId = evolutionChainId)
        pokemonLocalDataSource.insertPokemonInfo(pokemon)
        return pokemon
    }

    private suspend fun getLocalPokemonInfo(id: Int): PokemonInfoUiState {
        val pokemonInfo = pokemonLocalDataSource.fetchPokemonInfo(id)
        val pokemonStats = pokemonLocalDataSource.fetchPokemonInfoStat(id)
        val pokemonEvolutions = pokemonLocalDataSource.fetchPokemonInfoEvolution(id)
        val evolutionChain = pokemonEvolutions.map {
            val pokemon = pokemonLocalDataSource.fetchPokemonInfo(it.idPokemon)
            PokemonInfoEvolutionUiState(
                pokemon = pokemon.asUiState(),
                minLevel = it.minLevel
            )
        }
        val stats = pokemonStats.map {
            val statEnum = Enums.StatType.valueOf(it.name.replace("-", "_").uppercase())
            PokemonInfoStatUiState(
                name = statEnum.stat,
                baseState = it.baseState / 100f,
                color = statEnum.color
            )
        }
        return pokemonInfo.asUiState()
            .copy(evolutionChain = evolutionChain, baseStats = stats)
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