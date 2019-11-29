package com.mark.starwars.utils

import com.mark.starwars.model.Character
import io.reactivex.Completable
import io.reactivex.Observable

interface DatabaseRequests {
    fun getAllItems() : Observable<List<Character>>
    fun addItem(character: Character) : Completable
    fun deleteItem(character: Character) : Completable
}