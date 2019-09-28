package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mark.starwars.R
import com.mark.starwars.di.AppModule
import com.mark.starwars.di.DaggerAppComponent
import com.mark.starwars.di.NetModule
import com.mark.starwars.di.RoomModule
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.presenters.FavouriteFragmentPresenter
import com.mark.starwars.utils.FavouriteAdapter
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.FavouriteView
import javax.inject.Inject

class FavouriteListFragment : MvpAppCompatFragment(), FavouriteView {
    @Inject
    lateinit var apiService : RetrofitService
    @Inject
    lateinit var repository: Repository
    private lateinit var mAdapter : FavouriteAdapter
    @InjectPresenter
    lateinit var presenter: FavouriteFragmentPresenter
    @ProvidePresenter
    fun providePresenter():FavouriteFragmentPresenter{
        return FavouriteFragmentPresenter(repository = repository, apiService = apiService)
    }

    override fun onStart() {
        super.onStart()
        mAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FavouriteListFragment {
            return FavouriteListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = DaggerAppComponent.builder().appModule(AppModule(context)).roomModule(RoomModule()).netModule(
            NetModule()).build()
        component.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemview = inflater.inflate(R.layout.favourite_list, container, false)
        val recyclerView = itemview.findViewById(R.id.recyclerview) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        mAdapter = FavouriteAdapter(presenter)
        recyclerView.adapter = mAdapter
        return itemview
    }

    override fun loadList(list: List<Character>) {
        mAdapter.upLoadSearchResult(list)
    }


    override fun showDetails(c: Character) {
           activity!!.supportFragmentManager.beginTransaction()
               .addToBackStack(null)
               .replace(R.id.fragment_container, DetailFragment.newInstance(c))
               .commit()
    }

    override fun onResume() {
        super.onResume()
        Log.d("Zanovo", "zan")
        presenter.loadItems()
//        mAdapter.notifyDataSetChanged()
    }

}