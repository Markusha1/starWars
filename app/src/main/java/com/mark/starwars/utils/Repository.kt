package com.mark.starwars.utils

import com.mark.starwars.db.CharacterDataDao
import com.mark.starwars.model.Character
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject
constructor(private val budgetDao: CharacterDataDao) {

     suspend fun getAllItems(): List<Character> {
        return withContext(Dispatchers.IO) {
            budgetDao.getAllCharacters()
        }
     }

    fun addItem(character : Character) {
        GlobalScope.launch(Dispatchers.IO) {
            budgetDao.insertCharacter(character) }
    }

    fun deleteItem(character: Character) {
       GlobalScope.launch(Dispatchers.IO) {
           budgetDao.deleteCharacter(character.name) }
    }
}