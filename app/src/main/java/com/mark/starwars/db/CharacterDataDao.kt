package com.mark.starwars.db

import androidx.room.*
import com.mark.starwars.model.Character
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CharacterDataDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters(): Observable<List<Character>>

    @Insert
    fun insertCharacter(character: Character) : Completable

    @Query("DELETE FROM character WHERE name = :character")
    fun deleteCharacter(character: String) : Completable
}