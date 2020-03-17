package com.mark.starwars.di

import com.mark.starwars.data.db.CharacterRepository
import com.mark.starwars.data.db.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(charRepo : CharacterRepository) : Repository {
        return charRepo
    }
}