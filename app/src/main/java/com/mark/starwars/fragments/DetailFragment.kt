package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.mark.starwars.R
import com.mark.starwars.db.CharacterRepository
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.DetailPresenter
import com.mark.starwars.utils.Injector
import com.mark.starwars.views.IDetailView
import kotlinx.android.synthetic.main.detail_character.*
import org.w3c.dom.Text
import javax.inject.Inject

class DetailFragment : Fragment(), IDetailView {
    private lateinit var favButton : ImageButton
    private lateinit var textView : TextView
    private lateinit var genderView : TextView
    private lateinit var heightView : TextView
    private lateinit var birthView : TextView
    private lateinit var massView : TextView
    private lateinit var genderImage : ImageView
    lateinit var presenter : DetailPresenter
    lateinit var character : Character

    companion object {
        val TAG_CHARACTER = "DETAILS"

        @JvmStatic
        fun newInstance(character: Character) = DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAG_CHARACTER, character)
                }
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getSerializable(TAG_CHARACTER).let {
            character = (it as Character)
        }
        println("$character")
        presenter = DetailPresenter(this, character)
        Injector.get().inject(presenter)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.detail_character, container, false)
        favButton = v.findViewById(R.id.star_button) as ImageButton
        textView = v.findViewById(R.id.name_text) as TextView
        genderView = v.findViewById(R.id.gender_text) as TextView
        heightView = v.findViewById(R.id.height_text) as TextView
        birthView = v.findViewById(R.id.birth_year) as TextView
        massView = v.findViewById(R.id.mass_text) as TextView
        genderImage = v.findViewById(R.id.gender_image) as ImageView
        val backButton = v.findViewById(R.id.back_button) as ImageButton
        favButton.setOnClickListener{
            presenter.clickStar()
        }
        backButton.setOnClickListener {
            activity!!.supportFragmentManager.popBackStackImmediate()
        }
        presenter.init()
        return v
    }

    override fun initDetails(image : Int?, character: Character) {
        textView.text = character.name
        genderView.text = character.gender
        heightView.text = character.height
        birthView.text = character.birth_year
        massView.text = character.mass
        if (image != null) genderImage.setImageResource(image)
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