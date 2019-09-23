package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mark.starwars.R
import com.mark.starwars.di.AppModule
import com.mark.starwars.di.DaggerAppComponent
import com.mark.starwars.di.RoomModule
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.FavouriteFragmentPresenter
import com.mark.starwars.utils.FavouriteAdapter
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.FavouriteView
import javax.inject.Inject

class FavouriteListFragment : MvpAppCompatFragment(), FavouriteView {
    @Inject
    lateinit var characterRepository: Repository
    lateinit var mAdapter : FavouriteAdapter
    @InjectPresenter
    lateinit var presenter: FavouriteFragmentPresenter
    @ProvidePresenter
    fun providePresenter():FavouriteFragmentPresenter{
        return FavouriteFragmentPresenter(repository = characterRepository)
    }

    companion object {
        fun newInstance(): FavouriteListFragment {
            return FavouriteListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = DaggerAppComponent.builder().appModule(AppModule(context)).roomModule(RoomModule()).build()
        component.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favourite_list, container, false)
        val recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        mAdapter = FavouriteAdapter(presenter)
        recyclerView.adapter = mAdapter
        return view
    }

    override fun loadList(list: List<Character>) {
        mAdapter.addItems(list)
    }


    override fun showDetails(c: Character) {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailFragment.newInstance(c))
                .addToBackStack(null).commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadItems()
        mAdapter.notifyDataSetChanged()
    }
}