package com.mark.starwars.di


import com.mark.starwars.fragments.AllCharactersListFragment
import com.mark.starwars.fragments.DetailFragment
import com.mark.starwars.fragments.FavouriteListFragment
import com.mark.starwars.fragments.SearchCharacterFragment
import com.mark.starwars.presenters.AllCharacterPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, NetModule::class])
interface AppComponent {
    fun inject(detailFragment: DetailFragment)
    fun inject(allFragment: AllCharacterPresenter)
    fun inject(favFragment: FavouriteListFragment)
    fun inject(searchFragment: SearchCharacterFragment)

}