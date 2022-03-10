package org.sabaini.pokedex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.sabaini.pokedex.data.PokemonsSource
import org.sabaini.pokedex.ui.state.PokemonUiState
import org.sabaini.pokedex.util.Constants.PAGING_SIZE
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val source: PokemonsSource
) : ViewModel() {

    val pokeFlow: Flow<PagingData<PokemonUiState>> = Pager(PagingConfig(PAGING_SIZE)) {
        source
    }.flow
}