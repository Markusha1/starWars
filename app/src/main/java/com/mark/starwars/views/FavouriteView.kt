package com.mark.starwars.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mark.starwars.model.Character
import kotlinx.coroutines.Deferred
@StateStrategyType(AddToEndSingleStrategy::class)
interface FavouriteView : MvpView{
    fun loadList(list: List<Character>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDetails(c: Character)
}