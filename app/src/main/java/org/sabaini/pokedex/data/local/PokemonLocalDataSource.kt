package org.sabaini.pokedex.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
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
}