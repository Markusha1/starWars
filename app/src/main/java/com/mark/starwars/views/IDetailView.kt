package com.mark.starwars.views

import com.mark.starwars.model.Character

interface IDetailView : IView{
    fun setStarImage(isExist: Boolean)
    fun initDetails(image: Int?, character: Character)
    fun addFavourite()
    fun removeFavourite()
}