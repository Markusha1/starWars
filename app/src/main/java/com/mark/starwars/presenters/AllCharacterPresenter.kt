package com.mark.starwars.presenters

import android.util.Log
import com.mark.starwars.db.Repository
import com.mark.starwars.model.Character
import com.mark.starwars.utils.isAlreadyAdded
import com.mark.starwars.views.IAllCharacterView
import com.mark.starwars.views.IListView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AllCharacterPresenter(private var view : IListView?) : BasePresenter(view) {

    private val FIRST_PAGE = 1
    private var CURRENT_PAGE = FIRST_PAGE
    private val MAX_PAGE = 9
    private val compositeDisposable = CompositeDisposable()


    fun paginate(){
        if(CURRENT_PAGE != MAX_PAGE) {
            CURRENT_PAGE++
            loadCharacters()
            Log.d("page", "$CURRENT_PAGE")
        }
    }

    override fun loadCharacters(){
         view?.showProgress()
         compositeDisposable.add(repository.getCharactersFromApi(CURRENT_PAGE)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe { list ->
                 view?.hideProgress()
                 view?.loadList(list)
             }
         )
    }

    fun inDestroy(){
        compositeDisposable.dispose()
        view = null
    }

}