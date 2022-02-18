package org.sabaini.pokedex.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokedexRemoteDataSource(
    val pokedexApi: PokedexApi,
    val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPokedex(): List<PokedexResponse> =
        withContext(ioDispatcher) {
            pokedexApi.fetchPokedex()
        }
}

