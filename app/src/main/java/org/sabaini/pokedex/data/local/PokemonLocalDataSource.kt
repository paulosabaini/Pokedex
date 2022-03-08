package org.sabaini.pokedex.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemons(): List<PokemonLocalModel> =
        withContext(ioDispatcher) {
            pokemonDao.loadAll()
        }

    suspend fun insertPokemons(pokemons: List<PokemonLocalModel>) =
        withContext(ioDispatcher) {
            pokemonDao.saveAll(pokemons)
        }
}