package org.sabaini.pokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.sabaini.pokedex.ui.state.PokemonUiState
import javax.inject.Inject

class PokemonsSource @Inject constructor(private val repository: PokemonRepository) :
    PagingSource<Int, PokemonUiState>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonUiState> {
        return try {
            val nextPage = params.key ?: 0

            var pokemons = repository.getPokemonList(nextPage, false)
            if (pokemons.isEmpty()) {
                pokemons = repository.getPokemonList(nextPage, true)
            }

            LoadResult.Page(
                data = pokemons,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonUiState>): Int? {
        return state.anchorPosition
    }
}
