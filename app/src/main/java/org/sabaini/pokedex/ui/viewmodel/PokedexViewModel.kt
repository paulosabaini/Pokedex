package org.sabaini.pokedex.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
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

    fun calculateDominantColor(
        drawable: Drawable,
        onFinish: (Color) -> Unit
    ) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let {
                onFinish(Color(it))

            }
        }
    }
}