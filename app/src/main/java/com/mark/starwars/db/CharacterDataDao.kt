package com.mark.starwars.db

import androidx.room.*
import com.mark.starwars.model.Character

@Dao
interface CharacterDataDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters(): List<Character>

    @Insert
    fun insertCharacter(character: Character)

    @Query("DELETE FROM character WHERE name = :character")
    fun deleteCharacter(character: String)
}