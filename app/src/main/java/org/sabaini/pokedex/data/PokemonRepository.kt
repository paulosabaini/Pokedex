package org.sabaini.pokedex.data

import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) {
    suspend fun fetchPokemonList(): PokemonResponse =
        pokemonRemoteDataSource.fetchPokemonList()
}