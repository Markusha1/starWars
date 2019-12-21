package com.mark.starwars.utils

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.R
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.AllCharacterPresenter
import com.mark.starwars.presenters.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class CharacterAdapter(private val presenter : BasePresenter): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    private val items : MutableList<Character> = mutableListOf()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            val c = items[position]
            presenter.openDetails(items[position])
            Log.d("char", "$c")
        }
        holder.itemView.setOnLongClickListener {
            val character = items[position]
            presenter.isAlreadyAdded(character)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy{
                    if (it > 0) showAlreadyMenu(holder.itemView)
                    else showAddMenu(holder.itemView, character)
                }
            true
        }
    }

    private fun showAddMenu(v : View, character: Character) {
        val popupMenu = PopupMenu(v.context,v)
        popupMenu.menuInflater.inflate(R.menu.add_to_favourite_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.add) presenter.addCharacter(character)
            true
        }
        popupMenu.show()
    }

    private fun showAlreadyMenu(v : View){
        val popupMenu = PopupMenu(v.context,v)
        popupMenu.menuInflater.inflate(R.menu.already_added,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.already) {
                return@setOnMenuItemClickListener true
            }
            true
        }
        popupMenu.show()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.hero_name)

        fun bind(item: Character){
            name.text = item.name
        }
    }

    fun addItems(newItems : List<Character>){
        newItems.forEach{
            items.add(it)
            notifyItemInserted(items.size)
        }

    }

    fun clear(){
        items.clear()
        notifyDataSetChanged()
    }
}