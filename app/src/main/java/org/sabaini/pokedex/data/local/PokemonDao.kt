package org.sabaini.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(pokemons: List<PokemonLocalModel>)

    @Query("select * from PokemonLocalModel")
    fun loadAll(): List<PokemonLocalModel>

    @Query("delete from PokemonLocalModel")
    fun deleteAll()
}