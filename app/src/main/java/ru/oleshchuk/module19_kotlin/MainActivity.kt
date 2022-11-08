package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.oleshchuk.module19_kotlin.constants.Args
import ru.oleshchuk.module19_kotlin.constants.FragmentTags
import ru.oleshchuk.module19_kotlin.databinding.ActivityMainBinding
import ru.oleshchuk.module19_kotlin.model.Film

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun bottomNavigation(){
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_menu_fav -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.central_view, FavoritesFragment(), FragmentTags.TAG_FRAGMENT_FAVOURITES)
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else ->false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.central_view, HomeFragment(), FragmentTags.TAG_FRAGMENT_HOME)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1) {
            val snackbar = Snackbar.make(
                binding.mainLayout,
                "Are you sure you want to exit?", Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("Yes") {
                finish()
                super.onBackPressed()
            }
            snackbar.show()
        }
        else{
            super.onBackPressed()
        }
    }

    //open fragment on film
    fun openFilmDetails(film: Film){
        var bundle = Bundle()
        bundle.putParcelable(Args.FILM_ARG, film)
        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.central_view, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}