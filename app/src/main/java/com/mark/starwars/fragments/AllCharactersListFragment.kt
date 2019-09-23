package com.mark.starwars.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mark.starwars.utils.CharacterAdapter
import com.mark.starwars.utils.PaginationScrollListener
import com.mark.starwars.R
import com.mark.starwars.di.AppModule
import com.mark.starwars.di.DaggerAppComponent
import com.mark.starwars.di.RoomModule
import com.mark.starwars.model.Character
import com.mark.starwars.net.RetrofitService
import com.mark.starwars.presenters.AllCharacterPresenter
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.AllCharactersFragmentView
import kotlinx.android.synthetic.main.all_characters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllCharactersListFragment : MvpAppCompatFragment(), AllCharactersFragmentView {
    @Inject
    lateinit var repository: Repository
    @InjectPresenter
    lateinit var presenter: AllCharacterPresenter
    @ProvidePresenter
    fun provideAllPresenter(): AllCharacterPresenter{
        return AllCharacterPresenter(repository)
    }
    private val apiService = RetrofitService.create()
    var isLastPage = false
    var isLoading = false
    private lateinit var  mAdapter : CharacterAdapter
    lateinit var progress : ProgressBar


    companion object {
        fun newInstance(): AllCharactersListFragment {
            return AllCharactersListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = DaggerAppComponent.builder().appModule(AppModule(context)).roomModule(
            RoomModule()).build()
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.all_characters, container, false)
        val myRecyclerView = view.findViewById(R.id.character_list) as RecyclerView
        progress = view.findViewById(R.id.progressBar) as ProgressBar
        val layoutManager = LinearLayoutManager(activity)
        myRecyclerView.layoutManager = layoutManager
        mAdapter = CharacterAdapter(presenter)
        presenter.init()
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
        return view
    }


    override fun loadFirstList(page : Int) {
        progress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val requestCharacters = apiService.getCharacters(page).await()
            val characters = requestCharacters.body()
            Log.d("potok", "newList")
            withContext(Dispatchers.Main) {
                progress.visibility = View.INVISIBLE
                mAdapter.addItems(characters!!.results)
                mAdapter.notifyDataSetChanged()
            }
            isLoading = false
        }
    }

        override fun getMoreItems(page : Int) {
            progress.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                val requestCharacters = apiService.getCharacters(page)
                val characters = requestCharacters.await().body()
                withContext(Dispatchers.Main) {
                    progress.visibility = View.INVISIBLE
                    mAdapter.addItems(characters!!.results)
                    Log.d("list", "loadMore")
                }
                isLoading = false
            }
        }

    override fun showDetails(character : Character){
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailFragment.newInstance(character))
            .addToBackStack(null).commit()
        }


    override fun onDestroy() {
        mAdapter.clear()
        super.onDestroy()
    }

}