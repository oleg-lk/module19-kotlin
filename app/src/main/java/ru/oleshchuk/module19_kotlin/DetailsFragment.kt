package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.oleshchuk.module19_kotlin.databinding.FragmentDetailsBinding
import ru.oleshchuk.module19_kotlin.model.Film

class DetailsFragment : Fragment() {

    lateinit var binding : FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film = arguments?.getParcelable<Film>("film")
        film?.apply {
            binding.centralPoster.setImageResource(posterId)
            binding.detailsDesc.text = desc
            binding.detailsToolbar.title = name
        }
    }
}