package com.mark.starwars.presenters

import com.mark.starwars.views.IListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class SearchPresenter(var view: IListView?) : BasePresenter(view){
    private val disposable = CompositeDisposable()

    fun loadCharacters(input: String) {
        view?.showProgress()
         disposable += repository.getSearchResult(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view?.hideProgress()
                view?.loadList(it)
            },
                {
                    view?.showErrorDialog()
                })
    }

    override fun inStop() {
        disposable.dispose()
        view = null
    }

}