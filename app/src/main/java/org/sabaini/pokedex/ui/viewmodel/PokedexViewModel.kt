package org.sabaini.pokedex.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.sabaini.pokedex.data.PokemonRepository
import org.sabaini.pokedex.ui.state.PokedexItemUiState
import org.sabaini.pokedex.ui.state.PokedexUiState
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

    var pokedexUiState by mutableStateOf(PokedexUiState())
        private set

    private var fetchJob: Job? = null

    init {
        fetchPokedex()
    }

    fun fetchPokedex() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val pokemons = repository.fetchPokemonList()
                val pokedexItems = pokemons.results.map { PokedexItemUiState(it.name, it.url) }
                pokedexUiState = pokedexUiState.copy(
                    count = pokemons.count,
                    next = pokemons.next,
                    previous = pokemons.previous,
                    results = pokedexItems
                )
            } catch (ioe: IOException) {

            }
        }
    }
}