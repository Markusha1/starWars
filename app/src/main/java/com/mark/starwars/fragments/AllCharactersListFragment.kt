package com.mark.starwars.fragments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.utils.CharacterAdapter
import com.mark.starwars.utils.PaginationScrollListener
import com.mark.starwars.R
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.AllCharacterPresenter
import com.mark.starwars.utils.Injector
import com.mark.starwars.views.IListView

class AllCharactersListFragment : BaseFragment(), IListView {
    private val presenter: AllCharacterPresenter = AllCharacterPresenter(this)
    private var isLastPage = false
    private var isLoading = false
    private lateinit var  mAdapter : CharacterAdapter
    private lateinit var progress : ProgressBar


    companion object {
        fun newInstance(): AllCharactersListFragment {
            return AllCharactersListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Injector.get().inject(presenter)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemview = inflater.inflate(R.layout.all_characters, container, false)
        progress = itemview.findViewById(R.id.progressBar) as ProgressBar
        val myRecyclerView = itemview.findViewById(R.id.character_list) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        myRecyclerView.layoutManager = layoutManager
        mAdapter = CharacterAdapter(presenter)
        presenter.loadCharacters()
        myRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                presenter.paginate()
                Log.d("potok", "poshol")
            }
        })
        myRecyclerView.adapter = mAdapter
        return itemview
    }
    override fun loadList(characters: List<Character>) {
        mAdapter.addItems(characters)
    }

    override fun showDetails(character : Character){
        activity!!.supportFragmentManager.beginTransaction()
            .hide(this)
            .add(R.id.fragment_container, DetailFragment.newInstance(character))
            .addToBackStack(null)
            .commit()
        }

    override fun hideProgress() {
        progress.visibility = View.INVISIBLE
        isLoading = false
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
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

    override fun onBackPressed() {
        super.onBackPressed()
        activity?.finish()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.clear()
        presenter.inStop()
    }

}