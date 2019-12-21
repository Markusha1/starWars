package com.mark.starwars.presenters

import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.views.IFavouriteListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.coroutines.*
import javax.inject.Inject

class FavouritePresenter(private val viewState : IFavouriteListView) {
    @Inject
    lateinit var repository: CharacterRepository
    private val compositeDisposable = CompositeDisposable()

    fun openDetails(c: Character) {
        viewState.showDetails(c)
    }


    fun loadItems(){
        compositeDisposable.add(repository.getAllItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewState.loadList(it)
            })
    }

    fun delete(c: Character){
            repository.deleteItem(c)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

}