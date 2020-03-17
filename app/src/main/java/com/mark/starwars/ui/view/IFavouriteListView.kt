package com.mark.starwars.ui.view

import com.mark.starwars.data.model.Character

interface IFavouriteListView : IView {
    fun loadList(list: List<Character>)
    fun showDetails(character: Character)
}