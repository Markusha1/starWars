package com.mark.starwars.utils

import com.mark.starwars.model.Character
import io.reactivex.Observable

interface NetRequests {
    fun getCharactersFromApi(currentPage : Int) : Observable<List<Character>>
    fun getSearchResult(inputText : String) : Observable<List<Character>>
}