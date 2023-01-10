package ru.oleshchuk.module19_kotlin.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.oleshchuk.module19_kotlin.utils.FragmentAnimation
import ru.oleshchuk.module19_kotlin.databinding.FragmentFavoritesBinding

/**
 * A Favorites [Fragment] subclass.
 */
class FavoritesFragment(val position: Int) : Fragment() {

    private var _binding : FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**/
        FragmentAnimation.animateFragment(view, requireActivity(), position)

        //val recyclerView = RecyclerView(requireContext())
        //val adapter = FilmAdapter( object : FilmAdapter.OnItemClickListener{
        //    override fun onClick(film: Film?) {
        //        (activity as MainActivity).openFilmDetails(film)
        //    }
        //})
        //recyclerView.adapter = adapter
        //adapter.addFilms(films)
    }
}