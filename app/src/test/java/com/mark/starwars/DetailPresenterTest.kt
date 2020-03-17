package com.mark.starwars

import com.mark.starwars.data.db.Repository
import com.mark.starwars.data.model.Character
import com.mark.starwars.ui.presenter.DetailPresenter
import com.mark.starwars.ui.view.IDetailView
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.awaitility.kotlin.await
import org.junit.jupiter.api.*
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DetailPresenterTest {
    private val repo = Mockito.mock(Repository::class.java)
    private val view = Mockito.mock(IDetailView::class.java)
    private lateinit var presenter : DetailPresenter
    private val character = Character(name = "Daniel", mass = "700", gender = "female", birth_year = "1488", height = "177", id = 0, isFavourite = false)
    private lateinit var complete: Completable


    @BeforeAll
    fun setUp(){
        presenter = DetailPresenter(view, character)
        presenter.repository = repo
        complete = Completable.complete()
        Mockito.`when`(view.addFavourite()).then{println("Added!")}
        Mockito.`when`(view.removeFavourite()).then{println("Removed!")}
    }

    @Test
    @DisplayName("Adding the character in db")
    fun add_character_to_data_base(){
        Mockito.`when`(repo.addItem(character)).thenReturn(complete)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
            Schedulers.trampoline()
        }
        presenter.clickStar()
        await.until { character.isFavourite }

        Assertions.assertTrue(character.isFavourite)
        verify(view, times(1)).addFavourite()
    }

    @Test
    @DisplayName("Deleting the character in db")
    fun remove_character_from_data_base(){
        Mockito.`when`(repo.deleteItem(character)).thenReturn(complete)
        println("$character")

        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
            Schedulers.trampoline()
        }
        presenter.clickStar()
        await.until { !character.isFavourite }

        Assertions.assertFalse(character.isFavourite)
        verify(view, times(1)).removeFavourite()
    }
}