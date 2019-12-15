package com.mark.starwars.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.R
import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.presenters.SearchPresenter
import com.mark.starwars.utils.CharacterAdapter
import com.mark.starwars.utils.Injector
import com.mark.starwars.views.IListView
import kotlinx.android.synthetic.main.search_fragmnet.*
import javax.inject.Inject

class SearchCharacterFragment : Fragment(), IListView {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Injector.get().inject(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemview= inflater.inflate(R.layout.search_fragmnet, container, false)
        val myRecycler = itemview.findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        myRecycler.layoutManager = layoutManager
        mAdapter = CharacterAdapter(presenter)
        myRecycler.adapter = mAdapter
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
                if (!newText.isNullOrBlank()) {
                    newText.toLowerCase().trim()
                    presenter.loadCharacters(newText)
                }
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

}