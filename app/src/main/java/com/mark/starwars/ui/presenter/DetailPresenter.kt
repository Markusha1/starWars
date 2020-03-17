package com.mark.starwars.ui.presenter

import com.mark.starwars.data.db.Repository
import com.mark.starwars.data.model.Character
import com.mark.starwars.utils.GenderToImage
import com.mark.starwars.ui.view.IDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter(private val view: IDetailView, private val character: Character) {
    @Inject
    lateinit var repository: Repository
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