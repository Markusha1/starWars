package com.mark.starwars.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mark.starwars.R
import com.mark.starwars.di.AppModule
import com.mark.starwars.di.DaggerAppComponent
import com.mark.starwars.di.RoomModule
import com.mark.starwars.model.Character
import com.mark.starwars.presenters.DetailPresenter
import com.mark.starwars.utils.Repository
import com.mark.starwars.views.DetailFragmentView
import kotlinx.android.synthetic.main.detail_character.*
import javax.inject.Inject

class DetailFragment : MvpAppCompatFragment(), DetailFragmentView {
    @Inject
    lateinit var characterRepository : Repository
    val TAG_CHARACTER = "DETAILS"
    lateinit var character : Character
    @InjectPresenter
    lateinit var presenter : DetailPresenter
    @ProvidePresenter
    fun providePresenter(): DetailPresenter {
        return DetailPresenter(repository = characterRepository)
    }

    companion object {
        @JvmStatic
        fun newInstance(character: Character) = DetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(TAG_CHARACTER, character)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getSerializable(TAG_CHARACTER)?.let { character = it as Character }
        val component = DaggerAppComponent.builder().appModule(AppModule(context)).roomModule(RoomModule()).build()
        component.inject(this)
        Log.d("test", "$character")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.detail_character, container, false)
        presenter.init()
        val favButton = v.findViewById(R.id.star_button) as ImageButton
        val backButton = v.findViewById(R.id.back_button) as ImageButton
        favButton.setOnClickListener{ if (!character.isFavourite) {
            presenter.addToFavourite(character)
        } else {
            presenter.removeFromFavourite(character)
            }
        }
        backButton.setOnClickListener {activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FavouriteListFragment.newInstance())
            .addToBackStack(null).commit()}
        return v
    }

    override fun initDetails() {
        name_text.text = character.name
        gender_text.text = character.gender
        when {
            character.gender == "female" -> gender_image.setImageResource(R.drawable.ic_female_gender)
            character.gender == "n/a" -> gender_image.setImageResource(R.drawable.ic_na_gender)
            character.gender == "hermaphrodite" -> gender_image.setImageResource(R.drawable.ic_hermophrod_gender)
            character.gender == "male" -> gender_image.setImageResource(R.drawable.ic_male_gender)
        }
        height_text.text = character.height
        mass_text.text = character.mass
        year_text.text = character.birth_year
        if (character.isFavourite) star_button.setImageResource(R.drawable.ic_fill_favourite)
    }

    override fun addFavourite() {
        star_button.setImageResource(R.drawable.ic_fill_favourite)
        character.isFavourite = true
        Toast.makeText(context,"Add to favorite",Toast.LENGTH_SHORT).show()
    }

    override fun removeFavourite() {
        star_button.setImageResource(R.drawable.ic_hollow_favourite)
        Toast.makeText(context, "Remove to favorite", Toast.LENGTH_SHORT).show()
    }

}