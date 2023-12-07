package org.sabaini.pokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.sabaini.pokedex.presentation.pokedex.PokemonUiState
import org.sabaini.pokedex.util.Constants.ONE
import org.sabaini.pokedex.util.Constants.ZERO
import javax.inject.Inject

class PokemonsSource @Inject constructor(private val repository: PokemonRepository) :
    PagingSource<Int, PokemonUiState>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonUiState> {
        return try {
            val nextPage = params.key ?: ZERO

            var pokemons = repository.getPokemonList(nextPage, false)
            if (pokemons.isEmpty()) {
                pokemons = repository.getPokemonList(nextPage, true)
            }

            LoadResult.Page(
                data = pokemons,
                prevKey = if (nextPage == ZERO) null else nextPage - ONE,
                nextKey = nextPage.plus(ONE),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonUiState>): Int? {
        return state.anchorPosition
    }
}
