package com.mark.starwars.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.AllCharactersFragmentView
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

@InjectViewState
class AllCharacterPresenter(val repository: Repository, val apiService : RetrofitService): MvpPresenter<AllCharactersFragmentView>(){
    private val FIRST_PAGE = 1
    private var CURRENT_PAGE = FIRST_PAGE
    private val MAX_PAGE = 9

    fun paginate(){
        if(CURRENT_PAGE != MAX_PAGE) {
            CURRENT_PAGE++
            loadMoreCharacters()
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
        return runBlocking(Dispatchers.IO){
            var item = 0
            val list = repository.getAllItems()
            Log.d("COUNT", "$list")
            list.forEach {
                if (c.name == it.name) item += 1}
                Log.d("COUNT", "$item")
            return@runBlocking item
            }
    }

    fun loadFirstCharacters(){
        viewState.showProgress()
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val requestCharacters = apiService.getCharacters(CURRENT_PAGE).await()
                val characters = requestCharacters.body()
                Log.d("potok", "newList")
                withContext(Dispatchers.Main) {
                    viewState.hideProgress()
                    viewState.onGetDataSuccess(characters!!.results)
                }
            }
        } catch (e : SocketTimeoutException){
            viewState.showErrorDialog()
            }
        }

    private fun loadMoreCharacters(){
        viewState.showProgress()
        GlobalScope.launch(Dispatchers.IO) {
            val requestCharacters = apiService.getCharacters(CURRENT_PAGE)
            val characters = requestCharacters.await().body()
            withContext(Dispatchers.Main) {
                viewState.hideProgress()
                viewState.onGetDataSuccess(characters!!.results)
                Log.d("list", "loadMore")
            }
        }
    }
}