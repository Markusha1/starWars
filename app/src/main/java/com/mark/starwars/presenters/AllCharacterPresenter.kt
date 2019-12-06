package com.mark.starwars.presenters

import android.util.Log
import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.views.IAllCharacterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AllCharacterPresenter(private var view : IAllCharacterView?) {
    @Inject
    lateinit var repository: CharacterRepository
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
        view?.showDetails(character)
    }


    fun addCharacter(character: Character){
        character.isFavourite = true
        repository.addItem(character)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun isAlreadyAdded(c: Character) : Int{
        repository.isAlreadyExists(c)
            .subscribe()
    }

    fun loadFirstCharacters(){
         disposable = repository.getCharactersFromApi(CURRENT_PAGE)
             .subscribe (
                 {list ->
                 view?.onGetDataSuccess(list)
             },
                 {
                     view?.showErrorDialog()
                 }
             )
        disposable.addTo(compositeDisposable)
    }

    private fun loadMoreCharacters(){
         view?.showProgress()
         disposable = repository.getCharactersFromApi(CURRENT_PAGE)
             .subscribe { list ->
                 view?.hideProgress()
                 view?.onGetDataSuccess(list)
             }
        disposable.addTo(compositeDisposable)
    }

    fun inDestroy(){
        compositeDisposable.dispose()
        view = null
    }

}