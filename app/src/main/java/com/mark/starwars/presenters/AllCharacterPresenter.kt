package com.mark.starwars.presenters

import com.mark.starwars.views.IListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AllCharacterPresenter(private var view : IListView?) : BasePresenter(view) {
    private val FIRST_PAGE = 1
    private var CURRENT_PAGE = FIRST_PAGE
    private val MAX_PAGE = 9
    private val compositeDisposable = CompositeDisposable()


    fun paginate(){
        if(CURRENT_PAGE != MAX_PAGE) {
            CURRENT_PAGE++
            loadCharacters()
        }
    }

    fun loadCharacters(){
         view?.showProgress()
         compositeDisposable.add(repository.getCharactersFromApi(CURRENT_PAGE)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe ({ list ->
                 view?.hideProgress()
                 view?.loadList(list)
             },
                 {
                     view?.showErrorDialog()
                 })
         )
    }

    override fun inStop(){
        compositeDisposable.dispose()
        view = null
    }

}