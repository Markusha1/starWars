package com.mark.starwars.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.R
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.FavouriteFragmentPresenter

class FavouriteAdapter(val presenter : FavouriteFragmentPresenter) : RecyclerView.Adapter<FavouriteAdapter.FavoriteHolder>() {
    private val items : MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return FavoriteHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            val c = items[position]
            presenter.openDetails(items[position])
            Log.d("character", "$c")
        }
        holder.itemView.setOnLongClickListener {
            val character = items[position]
            showDeleteMenu(holder.itemView, character, position)
            true
        }
    }


    private fun showDeleteMenu(v : View, character: Character, position: Int){
        val popupMenu = PopupMenu(v.context,v)
        popupMenu.menuInflater.inflate(R.menu.delete_from_favourite_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.delete) {
                presenter.delete(character)
                items.removeAt(position)
                notifyItemRemoved(position)
            }
            true
        }
        popupMenu.show()
    }


    class FavoriteHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val name = itemView.findViewById<TextView>(R.id.hero_name)

        fun bind(item: Character) {
            name.text = item.name
        }

    }

    fun upLoadSearchResult(list : List<Character>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}