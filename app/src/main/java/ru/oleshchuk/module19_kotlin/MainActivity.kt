package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.oleshchuk.module19_kotlin.databinding.ActivityMainBinding
import ru.oleshchuk.module19_kotlin.model.Film

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object{
        const val home_fragment_tag = "HomeFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.central_view, HomeFragment(), home_fragment_tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1) {
            val snackbar = Snackbar.make(
                binding.mainLayout,
                "Are you sure you want to exit?", Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAnchorView(binding.mainScreenNavView)
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
        bundle.putParcelable("film", film)
        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.central_view, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}