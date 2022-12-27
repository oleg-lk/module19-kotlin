package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.oleshchuk.module19_kotlin.adapter.FilmAdapter
import ru.oleshchuk.module19_kotlin.animation.FilmItemAnimation
import ru.oleshchuk.module19_kotlin.animation.FragmentAnimation
import ru.oleshchuk.module19_kotlin.constants.FilmBd
import ru.oleshchuk.module19_kotlin.databinding.FragmentHomeBinding
import ru.oleshchuk.module19_kotlin.decor.FilmDecoration
import ru.oleshchuk.module19_kotlin.model.Film

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment(private val position: Int) : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var filmAdapter : FilmAdapter? = null
    private var rvFilmsView : RecyclerView? = null

    /*************************************************************************/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*************************************************************************/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**/
        initFilms()
        initSearch()
        /**/
        FragmentAnimation.animateFragment(view, requireActivity(), position)
    }

    /*************************************************************************/
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

    /***********************************************************************/
    init {
        filmAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener {
            override fun onClick(film: Film?) {
                (activity as MainActivity).openFilmDetails(film)
            }
        })
        filmAdapter?.addFilms(FilmBd.films)
    }

    /*************************************************************************/
    override fun onResume() {
        super.onResume()
    }

    /*************************************************************************/
    override fun onPause() {
        super.onPause()
    }

    /*************************************************************************/
    override fun onStart() {
        super.onStart()
    }

    /*************************************************************************/
    private fun initFilms() {
        rvFilmsView = view?.findViewById<RecyclerView>(R.id.films_view)

        /*set decorations*/
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.layer_divider, null)?.let {
            dividerItemDecoration.setDrawable(it)
            rvFilmsView?.addItemDecoration(dividerItemDecoration)
        }
        val filmDecoration = FilmDecoration(0)
        rvFilmsView?.addItemDecoration(filmDecoration)
        /*set adapter*/
        rvFilmsView?.adapter = filmAdapter
        rvFilmsView?.itemAnimator = FilmItemAnimation(requireContext())
    }
}