package com.mark.starwars.ui.view

import com.mark.starwars.data.model.Character

interface IDetailView : IView {
    fun setStarImage(isExist: Boolean)
    fun initDetails(image: Int?, character: Character)
    fun addFavourite()
    fun removeFavourite()
}