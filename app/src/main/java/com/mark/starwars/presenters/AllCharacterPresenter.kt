package com.mark.starwars.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mark.starwars.model.Character
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.AllCharactersFragmentView
import kotlinx.coroutines.*

@InjectViewState
class AllCharacterPresenter(var repository: Repository): MvpPresenter<AllCharactersFragmentView>(){
    private val FIRST_PAGE = 1
    private var CURRENT_PAGE = FIRST_PAGE
    private val MAX_PAGE = 9

    fun init(){
        viewState.loadFirstList(CURRENT_PAGE)
    }
    fun paginate(){
        if(CURRENT_PAGE != MAX_PAGE) {
            CURRENT_PAGE++
            viewState.getMoreItems(CURRENT_PAGE)
            Log.d("page", "$CURRENT_PAGE")
        }
    }

    fun openDetails(character : Character){
        viewState.showDetails(character)
    }


    fun addCharacter(character: Character){
        character.isFavourite = true
        GlobalScope.launch(Dispatchers.IO){
            repository.addItem(character)
        }
    }

    fun delete(character: Character){
        GlobalScope.launch(Dispatchers.IO){
            repository.deleteItem(character)
        }
    }

    fun findDuplicates(c: Character) : Int{
        var count = 0
        GlobalScope.launch (Dispatchers.IO){
            val list = repository.getAllItems()
            withContext(Dispatchers.Main) {
                list.forEach {
                    if (it.name == c.name) count += 1
                }
            }
        }
        Log.d("COUNT", "$count")
        return count
    }

}