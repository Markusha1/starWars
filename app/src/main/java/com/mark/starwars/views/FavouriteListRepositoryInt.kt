package com.mark.starwars.views

import com.mark.starwars.model.Character

interface FavouriteListRepositoryInt {
        val all: List<Character>
        fun addItem(c: Character)
        fun deleteItem(c: Character)
}