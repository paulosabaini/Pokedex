package org.sabaini.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.ui.state.PokemonUiState

@Entity
data class PokemonLocalModel(
    var page: Int = 0,
    @PrimaryKey
    val name: String,
    val url: String
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
    val evolutionChainId: String
)

@Entity(primaryKeys = ["idPokemon", "name", "baseState"])
data class PokemonInfoStatLocalModel(
    val idPokemon: Int,
    val name: String,
    val baseState: Int
)

@Entity(primaryKeys = ["evolutionChainId", "idPokemon", "minLevel"])
data class PokemonInfoEvolutionLocalModel(
    val evolutionChainId: Int,
    val idPokemon: Int,
    val minLevel: Int
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

fun PokemonInfoLocalModel.asUiState(): PokemonInfoUiState {
    return PokemonInfoUiState(
        id = this.id,
        name = this.name,
        types = this.types.split(","),
        description = this.description,
        height = this.height,
        weight = this.weight,
        baseStats = listOf(),
        evolutionChain = listOf()
    )
}