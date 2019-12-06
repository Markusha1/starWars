package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mark.starwars.R
import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.DetailPresenter
import com.mark.starwars.utils.Injector
import com.mark.starwars.views.IDetailView
import kotlinx.android.synthetic.main.detail_character.*
import javax.inject.Inject

class DetailFragment : Fragment(), IDetailView {
    private lateinit var favButton : ImageButton
    lateinit var character : Character
    lateinit var presenter : DetailPresenter

    companion object {
        val TAG_CHARACTER = "DETAILS"

        fun newInstance(character: Character) : DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply {
                putSerializable(TAG_CHARACTER, character)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getSerializable(TAG_CHARACTER)?.let { character = it as Character }
        presenter = DetailPresenter(this, character)
        Injector.get().inject(presenter)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.detail_character, container, false)
        presenter.init()
        favButton = v.findViewById(R.id.star_button) as ImageButton
        val backButton = v.findViewById(R.id.back_button) as ImageButton
        favButton.setOnClickListener{
            presenter.clickStar()
        }
        backButton.setOnClickListener {
            activity!!.supportFragmentManager.popBackStackImmediate()
        }
        return v
    }

    override fun initDetails(image : Int?, character: Character) {
        name_text.text = character.name
        gender_text.text = character.gender
        height_text.text = character.height
        birth_year.text = character.birth_year
        mass_text.text = character.mass
        if (image != null) gender_image.setImageResource(image)
    }

    override fun setStarImage(isExist: Boolean) {
        if (isExist) favButton.setImageResource(R.drawable.ic_fill_star)
        else favButton.setImageResource(R.drawable.ic_hollow_favourite)
    }

    override fun addFavourite() {
        star_button.setImageResource(R.drawable.ic_fill_favourite)
        Toast.makeText(context,"Add to favorite",Toast.LENGTH_SHORT).show()
    }

    override fun removeFavourite() {
        star_button.setImageResource(R.drawable.ic_hollow_favourite)
        Toast.makeText(context, "Remove to favorite", Toast.LENGTH_SHORT).show()
    }

}