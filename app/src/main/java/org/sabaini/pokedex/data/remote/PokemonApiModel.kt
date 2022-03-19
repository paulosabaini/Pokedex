package org.sabaini.pokedex.data.remote

import com.google.gson.annotations.SerializedName
import org.sabaini.pokedex.data.local.PokemonLocalModel
import org.sabaini.pokedex.ui.state.PokemonInfoStatUiState
import org.sabaini.pokedex.ui.state.PokemonInfoUiState
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Enums

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

fun PokemonInfoApiModel.asUiSate(): PokemonInfoUiState {
    return PokemonInfoUiState(
        id = this.id,
        name = this.name,
        types = this.types.map { it.type.name },
        description = BLANK,
        height = this.height,
        weight = this.weight,
        baseStats = this.stats.map { getUiStat(it) }
    )
}

private fun getUiStat(apiStat: PokemonInfoStatsApiModel): PokemonInfoStatUiState {
    val statEnum = Enums.StatType.valueOf(apiStat.stat.name.replace("-", "_").uppercase())
    return PokemonInfoStatUiState(
        name = statEnum.stat,
        baseState = apiStat.baseState / 100f,
        color = statEnum.color
    )
}