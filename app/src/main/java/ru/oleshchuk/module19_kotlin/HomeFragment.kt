package ru.oleshchuk.module19_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import ru.oleshchuk.module19_kotlin.adapter.FilmAdapter
import ru.oleshchuk.module19_kotlin.databinding.FragmentHomeBinding
import ru.oleshchuk.module19_kotlin.model.Film

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
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
        //return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilms()
    }

    private fun initFilms() {
        val films = arrayListOf<Film>(
            Film(
                "Доктор Ноу (фильм, 1962)  Dr. No",
                R.drawable.film1,
                "1962, боевик, приключения, триллер, Великобритания, США",
                1, 50
            ),
            Film(
                "Лок (фильм, 2013)  Locke",
                R.drawable.film2,
                "2013, драма, Великобритания, США",
                1, 25
            ),
            Film(
                "Мастер (фильм, 2012)",
                R.drawable.film3,
                "The Master 2012, драма, США",
                2, 18
            ),
            Film(
                "Меланхолия (фильм, 2011)",
                R.drawable.film4,
                "Melancholia 2011, драма, фантастика, Дания, Швеция, Франция, Германия",
                2, 15
            ),
            Film(
                "Темный рыцарь (фильм, 2008)",
                R.drawable.film5,
                "The Dark Knight 2008, боевик, драма, триллер, США, Великобритания",
                2, 32
            ),
            Film(
                "Властелин Колец: Две Крепости (фильм, 2002)",
                R.drawable.film6,
                "The Lord of the Rings: The Two Towers 2002, боевик, драма, приключения, США, Новая Зеландия",
                3, 21
            ),
            Film(
                "Интерстеллар (фильм, 2014)",
                R.drawable.film7,
                "Interstellar 2014, драма, приключения, фантастика, США, Великобритания, Канада",
                2, 49
            ),
            Film(
                "Пианист (фильм, 2002)",
                R.drawable.film8,
                "The Pianist 2002, биографический, драма, музыка, Франция, Польша, Германия, Великобритания",
                2, 30
            )
        )
        val filmAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener{
            override fun onClik(film: Film) {
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
        filmAdapter.addFilms(films)
    }
}