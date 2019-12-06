package com.mark.starwars.di

import com.mark.starwars.fragments.SearchCharacterFragment
import com.mark.starwars.presenters.AllCharacterPresenter
import com.mark.starwars.presenters.DetailPresenter
import com.mark.starwars.presenters.FavouritePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, NetModule::class])
interface AppComponent {
    fun inject(detailPresenter: DetailPresenter)
    fun inject(presenter: AllCharacterPresenter)
    fun inject(favPresenter: FavouritePresenter)
    fun inject(searchPresenter: SearchCharacterFragment)

}