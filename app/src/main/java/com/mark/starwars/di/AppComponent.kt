package com.mark.starwars.di

import com.mark.starwars.ui.presenter.AllCharacterPresenter
import com.mark.starwars.ui.presenter.DetailPresenter
import com.mark.starwars.ui.presenter.FavouritePresenter
import com.mark.starwars.ui.presenter.SearchPresenter
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