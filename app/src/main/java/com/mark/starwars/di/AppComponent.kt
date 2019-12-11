package com.mark.starwars.di

import com.mark.starwars.presenters.AllCharacterPresenter
import com.mark.starwars.presenters.DetailPresenter
import com.mark.starwars.presenters.FavouritePresenter
import com.mark.starwars.presenters.SearchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, NetModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(detailPresenter: DetailPresenter)
    fun inject(presenter: AllCharacterPresenter)
    fun inject(favPresenter: FavouritePresenter)
    fun inject(searchPresenter: SearchPresenter)

}