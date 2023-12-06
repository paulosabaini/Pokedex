package org.sabaini.pokedex.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.sabaini.pokedex.data.PokemonRepository
import org.sabaini.pokedex.ui.Destinations
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    private val pokemonName: String =
        checkNotNull(savedStateHandle[Destinations.POKEMON_SCREEN_ARGUMENT])

    private val _uiState = MutableStateFlow(PokemonInfoUiState())
    val uiState: StateFlow<PokemonInfoUiState> = _uiState

    init {
        fetchPokemonInfo(pokemonName)
    }

    private fun fetchPokemonInfo(name: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonInfo(name, false)?.let { pokemonInfo ->
                _uiState.value = pokemonInfo
            } ?: pokemonRepository.getPokemonInfo(name, true)?.let { pokemonInfo ->
                _uiState.value = pokemonInfo
            }
        }
    }
}
