package com.mark.starwars.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mark.starwars.views.MainActivityView

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {
    fun init(){
        viewState.setFirstFragment()
    }
}