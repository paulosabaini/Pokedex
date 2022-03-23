package org.sabaini.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePokemonInfo(pokemon: PokemonInfoLocalModel)

    @Query("select * from PokemonInfoLocalModel where id = :id")
    fun loadPokemonInfo(id: Int): PokemonInfoLocalModel

    @Query("select * from PokemonInfoLocalModel where name = :name")
    fun loadPokemonInfoByName(name: String): PokemonInfoLocalModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePokemonInfoStat(pokemonStat: List<PokemonInfoStatLocalModel>)

    @Query("select * from PokemonInfoStatLocalModel where idPokemon = :id")
    fun loadPokemonInfoStat(id: Int): List<PokemonInfoStatLocalModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePokemonInfoEvolution(pokemonEvolution: List<PokemonInfoEvolutionLocalModel>)

    @Query("select * from PokemonInfoEvolutionLocalModel where evolutionChainId = :evolutionChainId")
    fun loadPokemonInfoEvolution(evolutionChainId: Int): List<PokemonInfoEvolutionLocalModel>
}