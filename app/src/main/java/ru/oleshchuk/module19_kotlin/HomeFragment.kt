package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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

    lateinit var binding: FragmentHomeBinding
    private var filmAdapter : FilmAdapter? = null

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
        initSearch()
    }

    //search menu
    private fun initSearch() {
        binding.search.setOnClickListener {
            binding.search.isIconified = false
        }

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(p0: String?): Boolean {
                if((p0 == null) || p0.isEmpty()){
                    //all films
                    filmAdapter?.addFilms(FilmBd.films)
                    return true
                }
                //filter by names
                val filmList = FilmBd.films.filter { film ->
                    if(film.name!=null)
                        film.name.contains(p0, true)
                    else
                        false
                }
                //true if the query has been handled by the listener
                filmAdapter?.addFilms(filmList)
                return true
            }
        })
    }

    private fun initFilms() {
        filmAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener {
            override fun onClick(film: Film) {
                (activity as MainActivity).openFilmDetails(film)
            }
        })
        /*set decorations*/
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.layer_divider, null)?.let {
            dividerItemDecoration.setDrawable(it)
            binding.filmsView.addItemDecoration(dividerItemDecoration)
        }
        //val filmDecoration = FilmDecoration(0)
        //binding.filmsView.addItemDecoration(filmDecoration)
        /*set adapter*/
        binding.filmsView.adapter = filmAdapter
        filmAdapter?.addFilms(FilmBd.films)
    }
}