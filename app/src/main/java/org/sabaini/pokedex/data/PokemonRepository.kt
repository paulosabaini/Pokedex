package org.sabaini.pokedex.data

import org.sabaini.pokedex.data.local.PokemonLocalDataSource
import org.sabaini.pokedex.data.remote.PokemonListApiModel
import org.sabaini.pokedex.data.remote.PokemonRemoteDataSource
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource
) {
    suspend fun fetchPokemonList(): PokemonListApiModel =
        pokemonRemoteDataSource.fetchPokemonList()
}