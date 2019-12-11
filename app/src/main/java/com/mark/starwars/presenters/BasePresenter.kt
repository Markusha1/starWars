package com.mark.starwars.presenters

import com.mark.starwars.db.Repository
import com.mark.starwars.model.Character
import com.mark.starwars.utils.isAlreadyAdded
import com.mark.starwars.views.IListView
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


abstract class BasePresenter(private val view: IListView?) : IBasePresenter {
    @Inject
    open lateinit var repository: Repository

    override fun openDetails(character: Character) {
        view?.showDetails(character)
    }

    override fun isAlreadyAdded(character: Character): Single<Int> {
        return character.isAlreadyAdded(repository)
    }

    override fun addCharacter(character: Character) {
        character.isFavourite = true
        repository.addItem(character)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    abstract fun loadCharacters()
}