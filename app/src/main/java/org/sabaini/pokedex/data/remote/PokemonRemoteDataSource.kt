package org.sabaini.pokedex.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonList(): PokemonListApiModel =
        withContext(ioDispatcher) {
            pokemonApi.fetchPokemonList()
        }
}

