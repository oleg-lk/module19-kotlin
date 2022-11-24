package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import android.transition.Scene
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.oleshchuk.module19_kotlin.adapter.FilmAdapter
import ru.oleshchuk.module19_kotlin.constants.FilmBd
import ru.oleshchuk.module19_kotlin.databinding.FragmentHomeBinding
import ru.oleshchuk.module19_kotlin.decor.FilmDecoration
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
        val mainScene = Scene.getSceneForLayout(binding.root, R.layout.merge_home_screen, context)
        //mainScene.enter()
        val slideSearch = Slide(Gravity.TOP).addTarget(R.id.search).setDuration(200)
        val slideRv = Slide(Gravity.BOTTOM).addTarget(R.id.films_view).setDuration(300)
        TransitionManager.go(mainScene, TransitionSet()
            .addTransition(slideSearch)
            .addTransition(slideRv))
        initFilms()
        initSearch()
    }

    //search menu
    private fun initSearch() {
        val search = view?.findViewById<SearchView>(R.id.search)
        search?.setOnClickListener {
            (it as SearchView).isIconified = false
        }

        search?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
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
                    if(film.name != null)
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
            override fun onClick(film: Film?) {
                (activity as MainActivity).openFilmDetails(film)
            }
        })
        val filmsView = view?.findViewById<RecyclerView>(R.id.films_view)
        /*set decorations*/
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.layer_divider, null)?.let {
            dividerItemDecoration.setDrawable(it)
            filmsView?.addItemDecoration(dividerItemDecoration)
        }
        val filmDecoration = FilmDecoration(0)
        filmsView?.addItemDecoration(filmDecoration)
        /*set adapter*/
        filmsView?.adapter = filmAdapter
        filmAdapter?.addFilms(FilmBd.films)
    }
}