package com.mark.starwars.presenters

import com.mark.starwars.model.Character
import io.reactivex.Single

interface IBasePresenter {
    fun openDetails(character: Character)
    fun isAlreadyAdded(character: Character) : Single<Int>
    fun addCharacter(character: Character)
}