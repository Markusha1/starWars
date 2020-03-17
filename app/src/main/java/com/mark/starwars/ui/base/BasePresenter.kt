package com.mark.starwars.ui.base

import com.mark.starwars.data.db.Repository
import com.mark.starwars.data.model.Character
import com.mark.starwars.ui.view.IListView
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


abstract class BasePresenter(private val view: IListView?) :
    IBasePresenter {
    @Inject lateinit var repository: Repository

    override fun openDetails(character: Character) {
        view?.showDetails(character)
    }

    override fun isAlreadyAdded(character: Character): Single<Int> {
        return repository.isAlreadyExists(character)
    }

    abstract fun inStop()

    override fun addCharacter(character: Character) {
        character.isFavourite = true
        repository.addItem(character)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}