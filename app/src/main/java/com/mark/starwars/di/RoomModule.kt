package com.mark.starwars.di

import android.content.Context
import androidx.room.Room
import com.mark.starwars.db.AppDataBase
import com.mark.starwars.db.CharacterDataDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesAppDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "characters")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDao(database: AppDataBase): CharacterDataDao {
        return database.getDataDao()
    }
}