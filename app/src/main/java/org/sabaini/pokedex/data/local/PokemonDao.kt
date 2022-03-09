package org.sabaini.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(pokemons: List<PokemonLocalModel>)

    @Query("select * from PokemonLocalModel where page = :page")
    fun load(page: Int): List<PokemonLocalModel>

    @Query("select * from PokemonLocalModel where page <= :page")
    fun loadAll(page: Int): List<PokemonLocalModel>

    @Query("delete from PokemonLocalModel")
    fun deleteAll()
}