package com.mark.starwars.views

import com.arellomobile.mvp.MvpView

interface DetailFragmentView : MvpView {
    fun initDetails()
    fun addFavourite()
    fun removeFavourite()
}