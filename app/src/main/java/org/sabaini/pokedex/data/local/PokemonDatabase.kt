package org.sabaini.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonLocalModel::class, PokemonInfoLocalModel::class, PokemonInfoStatLocalModel::class, PokemonInfoEvolutionLocalModel::class],
    exportSchema = false,
    version = 6
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun PokemonDao(): PokemonDao
    abstract fun PokemonInfoDao(): PokemonInfoDao
}