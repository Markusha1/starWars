package com.mark.starwars.views

import com.arellomobile.mvp.MvpView
import com.mark.starwars.model.Character
import kotlinx.coroutines.Deferred

interface FavouriteView : MvpView{
    fun loadList(list: List<Character>)
    fun showDetails(c: Character)
}