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
import org.sabaini.pokedex.ui.state.PokemonUiState
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

    var pokemonUiState by mutableStateOf(listOf<PokemonUiState>())
        private set

    private var fetchJob: Job? = null

    init {
        fetchPokemons()
    }

    fun fetchPokemons() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                pokemonUiState = repository.getPokemonList(true)
            } catch (ioe: IOException) {

            }
        }
    }
}