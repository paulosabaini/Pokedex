package org.sabaini.pokedex.data.local

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sabaini.pokedex.presentation.pokemon.PokemonInfoUiState
import org.sabaini.pokedex.presentation.pokedex.PokemonUiState
import org.sabaini.pokedex.util.Constants.COMMA
import org.sabaini.pokedex.util.Constants.ZERO

@Entity
data class PokemonLocalModel(
    var page: Int = ZERO,
    @PrimaryKey
    val name: String,
    val url: String,
    val backgroundColor: Int? = null,
)

@Entity
data class PokemonInfoLocalModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val types: String,
    val description: String,
    val height: Int,
    val weight: Int,
    val evolutionChainId: String,
)

@Entity(primaryKeys = ["idPokemon", "name", "baseState"])
data class PokemonInfoStatLocalModel(
    val idPokemon: Int,
    val name: String,
    val baseState: Int,
)

@Entity(primaryKeys = ["evolutionChainId", "idPokemon", "minLevel"])
data class PokemonInfoEvolutionLocalModel(
    val evolutionChainId: Int,
    val idPokemon: Int,
    val minLevel: Int,
)

fun List<PokemonLocalModel>.asUiState(): List<PokemonUiState> {
    return map {
        PokemonUiState(
            page = it.page,
            name = it.name,
            url = it.url,
            backgroundColor = it.backgroundColor?.let { color -> Color(color) },
        )
    }
}

fun PokemonInfoLocalModel.asUiState(): PokemonInfoUiState {
    return PokemonInfoUiState(
        id = this.id,
        name = this.name,
        types = this.types.split(COMMA),
        description = this.description,
        height = this.height,
        weight = this.weight,
        baseStats = listOf(),
        evolutionChain = listOf(),
    )
}
