package com.mark.starwars.views

import com.mark.starwars.model.Character

interface IFavouriteListView : IView{
    fun loadList(list: List<Character>)
    fun showDetails(character: Character)
}