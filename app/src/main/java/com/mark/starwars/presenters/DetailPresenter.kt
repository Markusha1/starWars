package com.mark.starwars.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mark.starwars.model.Character
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.DetailFragmentView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@InjectViewState
class DetailPresenter(val repository: Repository) : MvpPresenter<DetailFragmentView>() {

    fun init(){
        viewState.initDetails()
    }

    fun addToFavourite(c: Character){
        repository.addItem(c)
        viewState.addFavourite()
    }

    fun removeFromFavourite(c : Character){
        repository.deleteItem(c)
        viewState.removeFavourite()
    }
}