package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.R
import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.presenters.FavouritePresenter
import com.mark.starwars.utils.FavouriteAdapter
import com.mark.starwars.utils.Injector
import com.mark.starwars.views.IFavouriteListView
import javax.inject.Inject

class FavouriteListFragment : Fragment(), IFavouriteListView {
    @Inject
    lateinit var apiService : RetrofitService
    @Inject
    lateinit var repository: CharacterRepository
    private lateinit var mAdapter : FavouriteAdapter
    lateinit var presenter: FavouritePresenter

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
        Injector.get().inject(presenter)
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


    override fun showDetails(character: Character) {
           activity!!.supportFragmentManager.beginTransaction()
               .addToBackStack(null)
               .replace(R.id.fragment_container, DetailFragment.newInstance(character))
               .commit()
    }

    override fun onResume() {
        super.onResume()
        Log.d("Zanovo", "zan")
        presenter.loadItems()
//        mAdapter.notifyDataSetChanged()
    }

}