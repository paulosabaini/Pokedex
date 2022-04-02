package org.sabaini.pokedex.data.local

import androidx.room.*

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

    @Query("update PokemonLocalModel set backgroundColor = :color where name = :name")
    fun updateBackgroundColor(name: String, color: Int?)

    @Query("select * from PokemonLocalModel where name = :name")
    fun getPokemon(name: String): PokemonLocalModel
}