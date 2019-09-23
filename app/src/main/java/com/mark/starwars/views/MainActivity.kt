package com.mark.starwars.views

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mark.starwars.R
import com.mark.starwars.fragments.AllCharactersListFragment
import com.mark.starwars.fragments.FavouriteListFragment
import com.mark.starwars.fragments.SearchCharacterFragment
import com.mark.starwars.presenters.MainActivityPresenter
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : MvpAppCompatActivity(), MainActivityView{
    @InjectPresenter
    lateinit var  presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        presenter.init()
        nav_bottom.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AllCharactersListFragment.newInstance())
                        .addToBackStack(null).commit()
                    true
                }
                R.id.nav_favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FavouriteListFragment.newInstance())
                        .addToBackStack(null).commit()
                    true
                }
                R.id.nav_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SearchCharacterFragment.newInstance())
                        .addToBackStack(null).commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun setFirstFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AllCharactersListFragment.newInstance())
            .addToBackStack(null).commit()
    }
}