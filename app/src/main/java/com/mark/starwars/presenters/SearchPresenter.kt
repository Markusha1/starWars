package com.mark.starwars.presenters

import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.views.ISearchView
import javax.inject.Inject

class SearchPresenter(val view: ISearchView) {
    @Inject
    lateinit var repository: CharacterRepository

}