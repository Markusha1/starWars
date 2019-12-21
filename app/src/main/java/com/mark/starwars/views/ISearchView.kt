package com.mark.starwars.views

interface ISearchView : IView{
    fun showProgressBar()
    fun hideProgressBar()
    fun loadResultist()
}