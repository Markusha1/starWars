package com.mark.starwars.presenters

import android.util.Log
import com.mark.starwars.model.Character
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.IAllCharacterView
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.SchedulerSupport.IO
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class AllCharacterPresenter(private val view : IAllCharacterView) {
    @Inject
    lateinit var repository: Repository
    private val FIRST_PAGE = 1
    private var CURRENT_PAGE = FIRST_PAGE
    private val MAX_PAGE = 9
    private val compositeDisposable = CompositeDisposable()
    private lateinit var disposable: Disposable

    fun paginate(){
        if(CURRENT_PAGE != MAX_PAGE) {
            CURRENT_PAGE++
            loadMoreCharacters()
            Log.d("page", "$CURRENT_PAGE")
        }
    }

    fun openDetails(character : Character){
        view.showDetails(character)
    }


    fun addCharacter(character: Character){
        character.isFavourite = true
        repository.addItem(character)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun findDuplicates(c: Character) : Int{
        return 0
    }

    fun loadFirstCharacters(){
         disposable = repository.getCharactersFromApi(CURRENT_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {characters ->
                view.onGetDataSuccess(characters)
            }
        disposable.addTo(compositeDisposable)
    }

    private fun loadMoreCharacters(){
        view.showProgress()
         disposable = repository.getCharactersFromApi(CURRENT_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{characters ->
                view.hideProgress()
                view.onGetDataSuccess(characters)
            }
        disposable.addTo(compositeDisposable)
    }

    fun inDestroy(){
        compositeDisposable.dispose()
    }
}