package com.mark.starwars.views


interface IAllCharacterView : IListView{
    fun hideProgress()
    fun showProgress()
    fun showErrorDialog()
}