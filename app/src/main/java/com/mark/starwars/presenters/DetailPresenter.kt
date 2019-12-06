package com.mark.starwars.presenters

import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.db.Repository
import com.mark.starwars.utils.GenderToImage
import com.mark.starwars.views.IDetailView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class DetailPresenter(private val view: IDetailView, private val character: Character) {
    @Inject
    lateinit var repository: CharacterRepository
    private val compositeDisposable = CompositeDisposable()


    fun init(){
        if (repository.isAlreadyExists(character) > 0) view.setStarImage(true)
        else view.setStarImage(false)
        val image = GenderToImage.convert(character.gender)
        view.initDetails(character = character, image = image)
    }

    private fun addToFavourite(){
        val addable = repository.addItem(character)
            .observeOn(Schedulers.io())
            .subscribe {
                character.isFavourite = true
                view.addFavourite()
            }
        compositeDisposable.add(addable)
    }

    private fun removeFromFavourite(){
        val removable = repository.deleteItem(character)
            .subscribe {
                character.isFavourite = false
                view.removeFavourite()
            }
        compositeDisposable.add(removable)
    }

    fun clickStar(){
        if (!character.isFavourite){
            addToFavourite()
        }
        else removeFromFavourite()
    }

}