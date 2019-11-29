package com.mark.starwars.presenters

import com.mark.starwars.model.Character
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.IDetailView
import kotlinx.coroutines.*
import javax.inject.Inject

class DetailPresenter(val viewState: IDetailView) {
    @Inject
    lateinit var repository: Repository


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