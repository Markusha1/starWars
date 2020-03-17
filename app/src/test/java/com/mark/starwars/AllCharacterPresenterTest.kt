package com.mark.starwars

import com.mark.starwars.data.db.Repository
import com.mark.starwars.data.model.Character
import com.mark.starwars.data.model.ResponseList
import com.mark.starwars.ui.presenter.AllCharacterPresenter
import com.mark.starwars.ui.view.IListView
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class AllCharacterPresenterTest {
    private val view = Mockito.mock(IListView::class.java)
    private val repo = Mockito.mock(Repository::class.java)
    private val resutlList = listOf(Character(name = "Arch", gender = "male", birth_year = "1917", mass = "13", height = "1000",id = 0), Character(name = "Boldeg", gender = "female", birth_year = "3021", mass = "534", height = "3012",id = 1))
    lateinit var presenter: AllCharacterPresenter
    lateinit var requestResult: ResponseList
    lateinit var characterList: Observable<List<Character>>

    @BeforeEach
    fun setUp(){
        presenter = AllCharacterPresenter(view)
        presenter.repository = repo
        requestResult = ResponseList(resutlList)
        characterList = Observable.fromArray(requestResult.results)
    }

    @Test
    fun get_characters_from_api() {
        Mockito.`when`(view.showProgress()).then{println("Show progress")}
        Mockito.`when`(view.hideProgress()).then{println("Hide progress")}
        Mockito.`when`(repo.getCharactersFromApi(1)).thenReturn(characterList)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
            Schedulers.trampoline()
        }
        presenter.loadCharacters()

        verify(view).loadList(resutlList)
        verify(view).showProgress()
        verify(view).hideProgress()
    }
}