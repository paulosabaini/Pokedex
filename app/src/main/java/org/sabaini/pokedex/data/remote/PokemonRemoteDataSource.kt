package org.sabaini.pokedex.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.sabaini.pokedex.util.Constants.PAGING_SIZE
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonList(page: Int): PokemonListApiModel =
        withContext(ioDispatcher) {
            pokemonApi.fetchPokemonList(limit = PAGING_SIZE, offset = page * PAGING_SIZE)
        }

    suspend fun fetchPokemonInfo(name: String): PokemonInfoApiModel =
        withContext(ioDispatcher) {
            pokemonApi.fetchPokemonInfo(name)
        }
}

