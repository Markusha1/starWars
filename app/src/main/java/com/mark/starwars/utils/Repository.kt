package com.mark.starwars.utils

import com.mark.starwars.db.CharacterDataDao
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject
constructor(private val budgetDao: CharacterDataDao, private val apiService : RetrofitService) : DatabaseRequests, NetRequests {

     override fun getAllItems(): Observable<List<Character>> {
        return budgetDao.getAllCharacters()
     }

    override fun addItem(character : Character) : Completable{
        return budgetDao.insertCharacter(character)
    }

    override fun deleteItem(character: Character) : Completable {
       return budgetDao.deleteCharacter(character.name)
    }

    override fun getCharactersFromApi(currentPage: Int): Observable<List<Character>> {
        return apiService.getCharacters(currentPage)
    }

    override fun getSearchResult(inputText: String): Observable<List<Character>> {
        return apiService.getSearchResult(inputText)
    }

}