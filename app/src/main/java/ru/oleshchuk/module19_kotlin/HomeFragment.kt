package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import ru.oleshchuk.module19_kotlin.adapter.FilmAdapter
import ru.oleshchuk.module19_kotlin.constants.FilmBd
import ru.oleshchuk.module19_kotlin.databinding.FragmentHomeBinding
import ru.oleshchuk.module19_kotlin.model.Film

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilms()
    }

    private fun initFilms() {
        val filmAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener{
            override fun onClick(film: Film) {
                (activity as MainActivity).openFilmDetails(film)
            }
        })
        /*set decorations*/
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.layer_divider, null)?.let {
            dividerItemDecoration.setDrawable(it)
            binding.filmsView.addItemDecoration(dividerItemDecoration)
        }
        //val filmDecoration = FilmDecoration(0)
        //binding.filmsView.addItemDecoration(filmDecoration)
        /*set adapter*/
        binding.filmsView.adapter = filmAdapter
        filmAdapter.addFilms(FilmBd.films)
    }
}