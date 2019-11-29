package com.mark.starwars.views

import com.mark.starwars.model.Character

interface IAllCharacterView {
    fun showDetails(character: Character)
    fun onGetDataSuccess(list: List<Character>)
    fun hideProgress()
    fun showProgress()
    fun showErrorDialog()
}