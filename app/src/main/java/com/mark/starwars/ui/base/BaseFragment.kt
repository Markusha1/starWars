package com.mark.starwars.ui.base
import androidx.fragment.app.*
import com.mark.starwars.fragments.IBackpressedSupport

open class BaseFragment : Fragment(), IBackpressedSupport {

    override fun onBackPressed() {}
}