package com.mark.starwars.ui.view

import com.mark.starwars.data.model.Character

interface IListView : IView {
    fun loadList(characters: List<Character>)
    fun showDetails(character: Character)
    fun showProgress()
    fun hideProgress()
    fun showErrorDialog()
}