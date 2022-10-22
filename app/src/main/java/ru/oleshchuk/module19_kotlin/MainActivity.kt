package ru.oleshchuk.module19_kotlin

import android.animation.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.*
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import ru.oleshchuk.module19_kotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView as BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun initListeners() {

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.main_screen_setting_menu -> {
                    Toast.makeText(this, R.string.main_screen_setting_menu_title, Toast.LENGTH_LONG)
                        .show()
                    true
                }
                else -> false
            }
        }

        findViewById<BottomNavigationView>(R.id.main_screen_nav_view).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_menu_fav -> {
                    Toast.makeText(this,
                        R.string.main_screen_nav_menu_fav,
                        Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_menu_later -> {
                    Toast.makeText(this,
                        R.string.main_screen_nav_menu_later,
                        Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_menu_selections -> {
                    Toast.makeText(this,
                        R.string.main_screen_nav_menu_selections,
                        Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun onSelectPoster(){

        val anim = AnimatorInflater.loadStateListAnimator(this, R.animator.poster_slection_animator)
        findViewById<CardView>(R.id.central_poster1).stateListAnimator = anim
        val poster2 = findViewById<CardView>(R.id.central_poster2)
        val poster3 = findViewById<CardView>(R.id.central_poster3)
        val poster4 = findViewById<CardView>(R.id.central_poster4)
        poster2.setOnClickListener {
            itView->
            itView.animate()
                .setDuration(200)
                .scaleX(0.8F)
                .scaleY(0.8F)
                .setListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(p0: Animator?) {
                    }
                    override fun onAnimationEnd(p0: Animator?) {
                        itView.scaleX = 1F
                        itView.scaleY = 1F
                    }
                    override fun onAnimationCancel(p0: Animator?) {
                    }
                    override fun onAnimationRepeat(p0: Animator?) {
                    }
                })
                .start()
        }
        val animation3 = AnimationUtils.loadAnimation(this, R.anim.central_poster_animation)
        poster3.setOnClickListener {
            it.startAnimation(animation3)
        }
        val anim4 = ValueAnimator.ofFloat(1F, 0.6F)
        val anim5 = ValueAnimator.ofFloat(0.6F, 1F )
        anim4.duration = 500
        anim4.interpolator = AnticipateInterpolator()
        anim4.addUpdateListener {
            poster4.scaleX = it.animatedValue as Float
            poster4.scaleY = it.animatedValue as Float
        }
        anim5.duration = 500
        anim5.interpolator = OvershootInterpolator()
        anim5.addUpdateListener {
            poster4.scaleX = it.animatedValue as Float
            poster4.scaleY = it.animatedValue as Float
        }
        val animSet = AnimatorSet()
        poster4.setOnClickListener {
            animSet.play(anim4)
            animSet.play(anim5).after(anim4)
            animSet.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
        onSelectPoster()
    }
}