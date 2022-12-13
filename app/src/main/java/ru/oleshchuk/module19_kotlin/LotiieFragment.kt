package ru.oleshchuk.module19_kotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import ru.oleshchuk.module19_kotlin.databinding.FragmentLotiieBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LotiieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LotiieFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lotiie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLotiieBinding.bind(view)
        binding.lottiieAnimation.addAnimatorListener(
            object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(p0: Animator?) {
                    (activity as MainActivity)?.start();
                }
            }
        )
    }
}