package ru.oleshchuk.module19_kotlin

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import ru.oleshchuk.module19_kotlin.constants.Args
import ru.oleshchuk.module19_kotlin.databinding.FragmentDetailsBinding
import ru.oleshchuk.module19_kotlin.model.Film

class DetailsFragment : Fragment() {

    private lateinit var binding : FragmentDetailsBinding
    private val _isFav = ObservableBoolean(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    init {
        enterTransition = Fade(Fade.MODE_IN).setDuration(500)
        returnTransition= Fade(Fade.MODE_OUT).setDuration(200)
    }

    /*set type image*/
    private fun setFabFavourite(isFav : Boolean) {
        if (isFav){
            binding.fabDetailsFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            binding.fabDetailsFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    /*************************************************************************/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film = arguments?.getParcelable<Film>(Args.FILM_ARG)
        film?.apply {
            binding.centralPoster.setImageResource(posterId)
            binding.detailsDesc.text = desc
            binding.detailsToolbar.title = name
            /*set type image*/
            //setFabFavourite (this.isFav)
        }
        /*proccess fab button*/
        binding.isFavourite = _isFav
        binding.fabDetailsFavourite.setOnClickListener {
            film?.apply {
                isFav = !isFav
                _isFav.set(!_isFav.get())
                //setFabFavourite(isFav)
            }
        }
        binding.fabDetailsShare.setOnClickListener {
            if(film!=null){
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT,
                    "film(${film.name}, desc(${film.desc})")
                //текстовые данные
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Choose"))
            }
        }
    }
}