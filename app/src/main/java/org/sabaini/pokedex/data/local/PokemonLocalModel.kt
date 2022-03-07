package org.sabaini.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonLocalModel(
    @PrimaryKey
    val name: String,
    val url: String
)