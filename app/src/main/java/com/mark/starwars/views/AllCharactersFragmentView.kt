package com.mark.starwars.views

import com.arellomobile.mvp.MvpView
import com.mark.starwars.model.Character

interface AllCharactersFragmentView : MvpView {
    fun showDetails(character : Character)
    fun showProgress()
    fun hideProgress()
    fun onGetDataSuccess(list : List<Character>)
}