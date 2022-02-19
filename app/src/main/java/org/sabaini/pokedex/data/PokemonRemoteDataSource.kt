package org.sabaini.pokedex.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokemonList(): PokemonResponse =
        withContext(ioDispatcher) {
            pokemonApi.fetchPokemonList()
        }
}

