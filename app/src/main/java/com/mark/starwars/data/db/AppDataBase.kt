package com.mark.starwars.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mark.starwars.data.db.CharacterDataDao
import com.mark.starwars.data.model.Character

@Database(entities = [Character::class], version = 3, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getDataDao() : CharacterDataDao
}