package com.mark.starwars.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mark.starwars.model.Character
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.DetailFragmentView
import kotlinx.coroutines.*

@InjectViewState
class DetailPresenter(val repository: Repository) : MvpPresenter<DetailFragmentView>() {

    fun init(){
        viewState.initDetails()
    }

    fun addToFavourite(c: Character){
        GlobalScope.launch(Dispatchers.IO) {
            repository.addItem(c)
        }
        viewState.addFavourite()
    }

    fun removeFromFavourite(c : Character){
        GlobalScope.launch(Dispatchers.IO){
            repository.deleteItem(c)
        }
        viewState.removeFavourite()
    }
}