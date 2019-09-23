package com.mark.starwars.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mark.starwars.fragments.AllCharactersListFragment
import com.mark.starwars.fragments.FavouriteListFragment

class FragmentListPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    private val titleList = listOf("All", "Favourites")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AllCharactersListFragment.newInstance()
            1 -> FavouriteListFragment.newInstance()
            else -> AllCharactersListFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
        }
}