package com.mark.starwars.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mark.starwars.R
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.AllCharacterPresenter

class CharacterAdapter(val presenter : AllCharacterPresenter): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
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
            var character = items[position]
            if(presenter.findDuplicates(character)>0){
                showDeleteMenu(holder.itemView, character)
            }else showAddMenu(holder.itemView, character)
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

    private fun showDeleteMenu(v : View, character: Character){
        val popupMenu = PopupMenu(v.context,v)
        popupMenu.menuInflater.inflate(R.menu.delete_from_favourite_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.delete) {
                presenter.delete(character)
            }
            true
        }
        popupMenu.show()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.hero_name)
        private val image = itemView.findViewById<ImageView>(R.id.profile_image)

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