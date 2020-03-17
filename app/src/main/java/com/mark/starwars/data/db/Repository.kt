package com.mark.starwars.data.db

import com.mark.starwars.data.model.Character
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {
    fun getAllItems() : Observable<List<Character>>
    fun addItem(character: Character) : Completable
    fun deleteItem(character: Character) : Completable
    fun getCharactersFromApi(currentPage : Int) : Observable<List<Character>>
    fun getSearchResult(inputText : String) : Observable<List<Character>>
    fun isAlreadyExists(character : Character) : Single<Int>
}