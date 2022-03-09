package org.sabaini.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sabaini.pokedex.ui.state.PokemonUiState

@Entity
data class PokemonLocalModel(
    var page: Int = 0,
    @PrimaryKey
    val name: String,
    val url: String
)

fun List<PokemonLocalModel>.asUiState(): List<PokemonUiState> {
    return map {
        PokemonUiState(
            page = it.page,
            name = it.name,
            url = it.url
        )
    }
}