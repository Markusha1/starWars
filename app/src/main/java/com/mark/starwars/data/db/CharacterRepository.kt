package com.mark.starwars.data.db

import com.mark.starwars.data.model.Character
import com.mark.starwars.data.net.RetrofitService
import io.reactivex.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val db: CharacterDataDao, private val apiService : RetrofitService) :
    Repository {

     override fun getAllItems(): Observable<List<Character>> {
        return db.getAllCharacters()
     }

    override fun addItem(character : Character) : Completable{
        return db.insertCharacter(character)
    }

    override fun deleteItem(character: Character) : Completable {
       return db.deleteCharacter(character.name)
    }

    override fun getCharactersFromApi(currentPage: Int): Observable<List<Character>> {
        return apiService.getCharacters(currentPage)
            .map{
                it.results
            }
    }

    override fun getSearchResult(inputText: String): Observable<List<Character>> {
        return apiService.getSearchResult(inputText)
            .map{ it.results }
    }

    override fun isAlreadyExists(character: Character): Single<Int> {
        return db.findDuplicates(character.name)
    }

}