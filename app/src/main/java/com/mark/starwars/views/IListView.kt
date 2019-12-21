package com.mark.starwars.views

import com.mark.starwars.model.Character

interface IListView : IView{
    fun loadList(characters: List<Character>)
    fun showDetails(character: Character)
    fun showProgress()
    fun hideProgress()
    fun showErrorDialog()
}