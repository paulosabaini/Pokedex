package org.sabaini.pokedex.data.remote

import com.google.gson.annotations.SerializedName
import org.sabaini.pokedex.data.local.PokemonInfoLocalModel
import org.sabaini.pokedex.data.local.PokemonInfoStatLocalModel
import org.sabaini.pokedex.data.local.PokemonLocalModel
import org.sabaini.pokedex.util.Constants.BLANK

data class PokemonListApiModel(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonApiModel>
)

data class PokemonApiModel(
    val name: String,
    val url: String
)

fun List<PokemonApiModel>.asLocalModel(page: Int): List<PokemonLocalModel> {
    return map {
        PokemonLocalModel(
            page = page,
            name = it.name,
            url = it.url
        )
    }
}

data class PokemonInfoApiModel(
    val id: Int,
    val name: String,
    val types: List<PokemonInfoTypesApiModel>,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonInfoStatsApiModel>,
    // val moves: List<PokemonInfoMovesApiModel>
)

fun PokemonInfoApiModel.asLocalModel(): PokemonInfoLocalModel {
    return PokemonInfoLocalModel(
        id = this.id,
        name = this.name,
        types = this.types.joinToString(separator = ",") { it.type.name },
        description = BLANK,
        height = this.height,
        weight = this.weight,
        evolutionChainId = BLANK
    )
}

fun List<PokemonInfoStatsApiModel>.asStatLocalModel(id: Int): List<PokemonInfoStatLocalModel> {
    return map {
        PokemonInfoStatLocalModel(
            idPokemon = id,
            name = it.stat.name,
            baseState = it.baseState
        )
    }
}

data class PokemonInfoTypesApiModel(
    val slot: Int,
    val type: PokemonInfoTypeApiModel
)

data class PokemonInfoTypeApiModel(
    val name: String,
    val url: String
)

data class PokemonInfoStatsApiModel(
    @SerializedName("base_stat")
    val baseState: Int,
    val effort: Int,
    val stat: PokemonInfoStatApiModel
)

data class PokemonInfoStatApiModel(
    val name: String,
    val url: String
)

data class PokemonInfoMovesApiModel(
    val move: PokemonInfoMoveApiModel,
    @SerializedName("version_group_details")
    val versionGroupDetails: PokemonInfoMoveDetailApiModel
)

data class PokemonInfoMoveApiModel(
    val name: String,
    val url: String
)

data class PokemonInfoMoveDetailApiModel(
    @SerializedName("level_learned_at")
    val levelLearnedAt: Int
)

data class PokemonInfoSpeciesApiModel(
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<PokemonInfoSpeciesFlavorTextApiModel>,
    @SerializedName("evolution_chain")
    val evolutionChain: PokemonInfoSpeciesEvolutionChain
)

data class PokemonInfoSpeciesFlavorTextApiModel(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: PokemonInfoSpeciesFlavorTextLanguageApiModel,
    val version: PokemonInfoSpeciesFlavorTextVersionApiModel
)

data class PokemonInfoSpeciesFlavorTextLanguageApiModel(
    val name: String,
    val url: String
)

data class PokemonInfoSpeciesFlavorTextVersionApiModel(
    val name: String,
    val url: String
)

data class PokemonInfoSpeciesEvolutionChain(
    val url: String
)

data class PokemonInfoEvolutionApiModel(
    val chain: PokemonInfoEvolutionChainApiModel
)

data class PokemonInfoEvolutionChainApiModel(
    @SerializedName("evolution_details")
    val evolutionDetails: List<PokemonInfoEvolutionDetailsApiModel>,
    @SerializedName("evolves_to")
    val evolvesTo: List<PokemonInfoEvolutionChainApiModel>,
    val species: PokemonInfoEvolutionSpecieApiModel
)

data class PokemonInfoEvolutionDetailsApiModel(
    @SerializedName("min_level")
    val minLevel: Int
)

data class PokemonInfoEvolutionSpecieApiModel(
    val name: String,
    val url: String
)