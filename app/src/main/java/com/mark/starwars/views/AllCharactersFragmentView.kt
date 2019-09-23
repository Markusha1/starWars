package com.mark.starwars.views

import com.arellomobile.mvp.MvpView
import com.mark.starwars.model.Character

interface AllCharactersFragmentView : MvpView {
    fun loadFirstList(page : Int)
    fun getMoreItems(page : Int)
    fun showDetails(character : Character)
}