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

    suspend fun getPokemonInfo(
        name: String,
        refresh: Boolean = false
    ): PokemonInfoUiState {
        return if (refresh) {
            externalScope.async {
                pokemonRemoteDataSource.fetchPokemonInfo(name).also { networkResult ->
                    val localPokemon = saveLocalPokemonInfo(networkResult)

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
                        saveLocalPokemonInfo(pokemonInfo)
                        val evolution =
                            PokemonInfoEvolutionLocalModel(
                                pokemonInfo.id,
                                minLevel,
                                localPokemon.evolutionChainId.toInt()
                            )
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
                getLocalPokemonInfo(name)
            }.await()
        } else {
            return getLocalPokemonInfo(name)
        }
    }

    private suspend fun saveLocalPokemonInfo(pokeInfo: PokemonInfoApiModel): PokemonInfoLocalModel {
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

    private suspend fun getLocalPokemonInfo(name: String): PokemonInfoUiState {
        val pokemonInfo = pokemonLocalDataSource.fetchPokemonInfoByName(name)
        pokemonInfo.let {
            val pokemonStats = pokemonLocalDataSource.fetchPokemonInfoStat(pokemonInfo.id)
            val pokemonEvolutions =
                pokemonLocalDataSource.fetchPokemonInfoEvolution(pokemonInfo.evolutionChainId.toInt())
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
    }
}