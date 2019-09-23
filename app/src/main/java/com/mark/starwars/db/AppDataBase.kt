package com.mark.starwars.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mark.starwars.model.Character

@Database(entities = [Character::class], version = 5, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getDataDao() : CharacterDataDao
}