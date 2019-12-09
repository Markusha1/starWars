package com.mark.starwars.presenters

import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.db.Repository
import com.mark.starwars.utils.GenderToImage
import com.mark.starwars.views.IDetailView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

class DetailPresenter(private val view: IDetailView, private val character: Character) {
    @Inject
    lateinit var repository: CharacterRepository
    private val compositeDisposable = CompositeDisposable()


    fun init(){
        inDatabase(character)
        val image = GenderToImage.convert(character.gender)
        view.initDetails(character = character, image = image)
    }

    private fun addToFavourite(){
        val addable = repository.addItem(character)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                character.isFavourite = true
                view.addFavourite()
            }
        compositeDisposable.add(addable)
    }

    private fun removeFromFavourite(){
        val removable = repository.deleteItem(character)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    private fun inDatabase(c: Character) {
        compositeDisposable.add(repository.isAlreadyExists(c)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                if (it > 0) {
                    view.setStarImage(true)
                    character.isFavourite = true
                }
                else {
                    view.setStarImage(false)
                }
            })
    }

}