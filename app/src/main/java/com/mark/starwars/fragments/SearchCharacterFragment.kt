package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
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
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.presenters.FavouriteFragmentPresenter
import com.mark.starwars.utils.FavouriteAdapter
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.FavouriteView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchCharacterFragment : MvpAppCompatFragment(), FavouriteView {
    private val apiService = RetrofitService.create()
    @Inject
    lateinit var repository: Repository
    @InjectPresenter
    lateinit var presenter : FavouriteFragmentPresenter
    @ProvidePresenter
    fun providePresenter():FavouriteFragmentPresenter {
        return FavouriteFragmentPresenter(repository = repository)
    }
    lateinit var mAdapter : FavouriteAdapter


    companion object {
        fun newInstance(): SearchCharacterFragment {
            return SearchCharacterFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = DaggerAppComponent.builder().appModule(AppModule(context)).roomModule(
            RoomModule()
        ).build()
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.search_fragmnet, container, false)
        val myRecycler = view.findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        myRecycler.layoutManager = layoutManager
        mAdapter = FavouriteAdapter(presenter)
        myRecycler.adapter = mAdapter
        presenter.loadItems()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem.actionView as SearchView
        searchView.focusable = View.FOCUSABLE
        searchView.onActionViewExpanded()
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var inputText = newText!!.toLowerCase().trim()
                GlobalScope.launch(Dispatchers.IO) {
                    val response = apiService.getSeatchResult(inputText).await()
                    val results = response.body()?.results
                    withContext(Dispatchers.Main){
                        mAdapter.upLoadSearchResult(results!!)
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun loadList(list: List<Character>) {
    }

    override fun showDetails(c: Character) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailFragment.newInstance(c))
            .addToBackStack(null).commit()
    }
}