package org.sabaini.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonLocalModel::class], exportSchema = false, version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun PokemonDao(): PokemonDao
}