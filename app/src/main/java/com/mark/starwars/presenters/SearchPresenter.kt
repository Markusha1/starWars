package com.mark.starwars.presenters

import com.mark.starwars.views.IListView

class SearchPresenter(val view: IListView?) : BasePresenter(view){

    override fun loadCharacters() {
        repository.getSearchResult()
    }


}