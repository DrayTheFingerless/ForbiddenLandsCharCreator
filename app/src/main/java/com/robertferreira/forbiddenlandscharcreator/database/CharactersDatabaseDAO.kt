package com.robertferreira.forbiddenlandscharcreator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.robertferreira.forbiddenlandscharcreator.FLCharacter

@Dao
interface CharactersDatabaseDAO {

    @Insert
    fun insert(character : FLCharacter)

    @Update
    fun update(character : FLCharacter)

    @Query("SELECT * FROM characters_table WHERE charId = :key")
    fun get(key : Long) : FLCharacter

    @Query("DELETE FROM characters_table")
    fun clear()

    @Query("SELECT * FROM characters_table ORDER BY charID DESC")
    fun getAllCharacters() : List<FLCharacter>

    @Query("SELECT * FROM characters_table ORDER BY charId DESC LIMIT 1")
    fun getLastCharacter() : FLCharacter

}