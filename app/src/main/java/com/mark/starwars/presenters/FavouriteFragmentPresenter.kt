package com.mark.starwars.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mark.starwars.model.Character
import com.mark.starwars.utils.Repository

import com.mark.starwars.views.FavouriteView
import kotlinx.coroutines.*

@InjectViewState
class FavouriteFragmentPresenter(private val repository: Repository) : MvpPresenter<FavouriteView>() {

    fun openDetails(c: Character) {
        viewState.showDetails(c)
    }

    fun loadItems() {
        GlobalScope.launch(Dispatchers.IO) {val l =  repository.getAllItems()
        withContext(Dispatchers.Main){
            viewState.loadList(l)
            }
        }
    }

    fun delete(c: Character){
        GlobalScope.launch(Dispatchers.IO){
            repository.deleteItem(c)
        }
    }

}