package com.mark.starwars.db

import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val db: CharacterDataDao, private val apiService : RetrofitService) : Repository {

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
            .subscribeOn(Schedulers.io())
            .map{ it.results }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSearchResult(inputText: String): Observable<List<Character>> {
        return apiService.getSearchResult(inputText)
            .map{ it.results }
    }

    override fun isAlreadyExists(character: Character): Single<Int> {
        return db.findDuplicates(character.name)
    }

}