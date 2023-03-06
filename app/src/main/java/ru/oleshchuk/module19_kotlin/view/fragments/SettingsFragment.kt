package ru.oleshchuk.module19_kotlin.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.data.AppConsts
import ru.oleshchuk.module19_kotlin.databinding.FragmentHomeBinding
import ru.oleshchuk.module19_kotlin.databinding.FragmentSettingsBinding
import ru.oleshchuk.module19_kotlin.utils.FragmentAnimation
import ru.oleshchuk.module19_kotlin.viewmodel.SettingsFragmentViewModel

class SettingsFragment(private val position: Int) : Fragment() {
    //model
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }
    lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentAnimation.animateFragment(view, requireActivity(), position)
        viewModel.categoryLifeData.observe(viewLifecycleOwner){
            str->
            when(str){
                POPULAR_CATEGORY -> binding.radioGroup.check(R.id.rb_popular)
                TOP_RATED_CATEGORY -> binding.radioGroup.check(R.id.rb_top)
                UPCOMING_CATEGORY -> binding.radioGroup.check(R.id.rb_soon)
                NOW_PLAYING_CATEGORY -> binding.radioGroup.check(R.id.rb_playing)
            }
        }
        binding.radioGroup.setOnCheckedChangeListener {
                _, checkedId ->
            when(checkedId) {
                R.id.rb_popular->viewModel.putCategory(POPULAR_CATEGORY)
                R.id.rb_top->viewModel.putCategory(TOP_RATED_CATEGORY)
                R.id.rb_soon->viewModel.putCategory(UPCOMING_CATEGORY)
                R.id.rb_playing->viewModel.putCategory(NOW_PLAYING_CATEGORY)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            SettingsFragment(position)

        private const val POPULAR_CATEGORY = "popular"
        private const val TOP_RATED_CATEGORY = "top_rated"
        private const val UPCOMING_CATEGORY = "upcoming"
        private const val NOW_PLAYING_CATEGORY = "now_playing"
    }
}