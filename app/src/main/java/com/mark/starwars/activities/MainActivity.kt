package com.mark.starwars.activities

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mark.starwars.R
import com.mark.starwars.fragments.AllCharactersListFragment
import com.mark.starwars.fragments.FavouriteListFragment
import com.mark.starwars.fragments.IBackpressedSupport
import com.mark.starwars.fragments.SearchCharacterFragment
import com.mark.starwars.views.IActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), IActivity {

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
                        .add(R.id.fragment_container, AllCharactersListFragment.newInstance(), "fragment")
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

    override fun hideNavBottom(){
        nav_bottom.visibility = View.INVISIBLE
    }

    override fun showNavBottom(){
        nav_bottom.visibility = View.VISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.isCanceled){
        nav_bottom.visibility = View.VISIBLE
        }
        return super.onKeyDown(keyCode, event)

    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.fragment_container) is IBackpressedSupport ->
                (supportFragmentManager.findFragmentById(R.id.fragment_container) as IBackpressedSupport).onBackPressed()
            else -> finish()
        }
    }

}