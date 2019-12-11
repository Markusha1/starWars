package com.mark.starwars.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mark.starwars.R
import com.mark.starwars.fragments.AllCharactersListFragment
import com.mark.starwars.fragments.FavouriteListFragment
import com.mark.starwars.fragments.IBackpressedSupport
import com.mark.starwars.fragments.SearchCharacterFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var fm : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        fm = supportFragmentManager
        setFirstFragment()
        nav_bottom.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_list -> {
                    fm.beginTransaction()
                        .replace(R.id.fragment_container, AllCharactersListFragment.newInstance(), "fragment")
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_favourites -> {
                    fm.beginTransaction()
                        .replace(R.id.fragment_container, FavouriteListFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.nav_search -> {
                    fm.beginTransaction()
                        .replace(R.id.fragment_container, SearchCharacterFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

        fun setFirstFragment() {
        fm.beginTransaction()
            .replace(R.id.fragment_container, AllCharactersListFragment.newInstance())
            .commit()
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.fragment_container) is IBackpressedSupport ->
                (supportFragmentManager.findFragmentById(R.id.fragment_container) as IBackpressedSupport).onBackPressed()
            else -> finish()
        }

    }
}