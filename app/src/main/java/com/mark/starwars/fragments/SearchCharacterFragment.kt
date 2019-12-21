package com.mark.starwars.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.R
import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.presenters.SearchPresenter
import com.mark.starwars.utils.CharacterAdapter
import com.mark.starwars.utils.Injector
import com.mark.starwars.views.IListView
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchCharacterFragment : BaseFragment(), IListView {
    @Inject
    lateinit var apiService: RetrofitService
    @Inject
    lateinit var repository: CharacterRepository
    private val presenter = SearchPresenter(this)
    private lateinit var mAdapter : CharacterAdapter


    companion object {
        fun newInstance(): SearchCharacterFragment {
            return SearchCharacterFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Injector.get().inject(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemview= inflater.inflate(R.layout.search_fragment, container, false)
        val myRecycler = itemview.findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = GridLayoutManager(activity, 2)
        myRecycler.layoutManager = layoutManager
        mAdapter = CharacterAdapter(presenter)
        myRecycler.adapter = mAdapter
//        val searchView = itemview.findViewById<SearchView>(R.id.searchView)
//        searchView.focusable = View.FOCUSABLE
//        searchView.queryHint = resources.getString(R.string.query_hint)
//        searchView.onActionViewExpanded()
//        searchView.setOnQueryTextListener(this)
        val tb = itemview.findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        (activity as AppCompatActivity).setSupportActionBar(tb)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        setHasOptionsMenu(true)
        return itemview
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
                val inputText = newText!!.toLowerCase().trim()
                presenter.loadCharacters(inputText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun loadList(characters: List<Character>) {
        mAdapter.addItems(characters)
    }

    override fun showDetails(character: Character) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .hide(this)
            .add(R.id.fragment_container, DetailFragment.newInstance(character))
            .addToBackStack(null)
            .commit()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
        mAdapter.clear()
    }

    override fun hideProgress() {
        progress.visibility = View.INVISIBLE
    }

    override fun showErrorDialog() {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(R.string.connection_error)
            .setNegativeButton(R.string.ok){ dialogInterface, _ ->
                dialogInterface.cancel()
            }
        val dialog = alertDialog.create()
        dialog.show()
    }

    override fun onStop() {
        super.onStop()
        presenter.inStop()
    }

    override fun onBackPressed() {
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        return false
//    }
//
//    override fun onQueryTextChange(inputName: String?): Boolean {
//        if(!inputName.isNullOrEmpty()) {
//            val request = inputName.trim().toLowerCase()
//            presenter.loadCharacters(request)
//        }
//        else mAdapter.clear()
//        return true
//    }

}