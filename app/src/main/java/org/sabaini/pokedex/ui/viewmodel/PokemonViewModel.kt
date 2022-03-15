package org.sabaini.pokedex.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sabaini.pokedex.data.PokemonRepository
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    var pokemonInfoUiState by mutableStateOf(PokemonInfoUiState())
        private set

    fun fetchPokemonInfo(name: String) {
        viewModelScope.launch {
            pokemonInfoUiState = pokemonRepository.getPokemonInfo(name)
        }
    }
}