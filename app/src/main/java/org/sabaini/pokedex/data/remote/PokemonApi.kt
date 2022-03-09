package org.sabaini.pokedex.data.remote

import org.sabaini.pokedex.util.Constants.PAGING_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = PAGING_SIZE,
        @Query("offset") offset: Int = 0
    ): PokemonListApiModel
}