package com.mark.starwars.ui.base

import com.mark.starwars.data.model.Character
import io.reactivex.Single

interface IBasePresenter {
    fun openDetails(character: Character)
    fun isAlreadyAdded(character: Character) : Single<Int>
    fun addCharacter(character: Character)
}