package com.mark.starwars.presenters

import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.views.IFavouriteListView

import kotlinx.coroutines.*
import javax.inject.Inject

class FavouritePresenter(private val viewState : IFavouriteListView) {
    @Inject
    lateinit var repository: CharacterRepository

    fun openDetails(c: Character) {
        viewState.showDetails(c)
    }

    fun loadItems() {

    }

    fun loadItems(title : String){
        val response = repository.getSearchResult(title)
    }

    fun delete(c: Character){
        GlobalScope.launch(Dispatchers.IO){
            repository.deleteItem(c)
        }
    }

}