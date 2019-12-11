package com.mark.starwars.fragments

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), IBackpressedSupport {

    override fun onBackPressed() {}
}