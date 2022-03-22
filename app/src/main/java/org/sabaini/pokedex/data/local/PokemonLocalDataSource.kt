package org.sabaini.pokedex.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemons(page: Int): List<PokemonLocalModel> =
        withContext(ioDispatcher) {
            pokemonDao.load(page)
        }

    suspend fun insertPokemons(pokemons: List<PokemonLocalModel>) =
        withContext(ioDispatcher) {
            pokemonDao.saveAll(pokemons)
        }

    suspend fun fetchPokemonInfo(id: Int): PokemonInfoLocalModel =
        withContext(ioDispatcher) {
            pokemonInfoDao.loadPokemonInfo(id)
        }

    suspend fun insertPokemonInfo(pokemon: PokemonInfoLocalModel) =
        withContext(ioDispatcher) {
            pokemonInfoDao.savePokemonInfo(pokemon)
        }

    suspend fun fetchPokemonInfoStat(id: Int): List<PokemonInfoStatLocalModel> =
        withContext(ioDispatcher) {
            pokemonInfoDao.loadPokemonInfoStat(id)
        }

    suspend fun insertPokemonInfoStat(pokemonStat: List<PokemonInfoStatLocalModel>) =
        withContext(ioDispatcher) {
            pokemonInfoDao.savePokemonInfoStat(pokemonStat)
        }

    suspend fun fetchPokemonInfoEvolution(id: Int): List<PokemonInfoEvolutionLocalModel> =
        withContext(ioDispatcher) {
            pokemonInfoDao.loadPokemonInfoEvolution(id)
        }

    suspend fun insertPokemonInfoEvolution(pokemonEvolution: List<PokemonInfoEvolutionLocalModel>) =
        withContext(ioDispatcher) {
            pokemonInfoDao.savePokemonInfoEvolution(pokemonEvolution)
        }
}