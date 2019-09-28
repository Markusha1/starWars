package com.mark.starwars.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mark.starwars.model.Character
@StateStrategyType(AddToEndSingleStrategy::class)
interface AllCharactersFragmentView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDetails(character : Character)
    fun showProgress()
    fun hideProgress()
    fun onGetDataSuccess(list : List<Character>)
    fun showErrorDialog()
}