package ru.oleshchuk.module19_kotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.oleshchuk.module19_kotlin.data.Args
import ru.oleshchuk.module19_kotlin.data.FragmentTags
import ru.oleshchuk.module19_kotlin.databinding.ActivityMainBinding
import ru.oleshchuk.module19_kotlin.domain.Film
import ru.oleshchuk.module19_kotlin.view.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /*************************************************************************/
    private fun getFragment(tag: String) : Fragment? {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        return fragment?: when (tag) {
                FragmentTags.TAG_FRAGMENT_HOME -> HomeFragment(1)
                FragmentTags.TAG_FRAGMENT_FAVOURITES -> FavoritesFragment(2)
                FragmentTags.TAG_FRAGMENT_LATER -> LaterFragment(3)
                FragmentTags.TAG_FRAGMENT_COLLECTION -> CollectionFragment(4)
                FragmentTags.TAG_FRAGMENT_DETAILS -> DetailsFragment()
                FragmentTags.TAG_FRAGMENT_LOTIIE -> LotiieFragment()
                else -> null
            }
    }

    /*************************************************************************/
    private fun replaceFragment(tag: String, bundle: Bundle? = null)
    {
        val fragment = getFragment(tag) ?: return
        if(bundle!=null){
            fragment.arguments = bundle
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.central_view, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    /*************************************************************************/
    private fun addFragment(tag: String)
    {
        val fragment = getFragment(tag) ?: return
        /**/
        supportFragmentManager
            .beginTransaction()
            .add(R.id.central_view, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    /*************************************************************************/
    private fun bottomNavigation(){

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_menu_home->{
                    replaceFragment(FragmentTags.TAG_FRAGMENT_HOME)
                    true
                }
                R.id.nav_menu_fav -> {
                    replaceFragment(FragmentTags.TAG_FRAGMENT_FAVOURITES)
                    true
                }
                R.id.nav_menu_later -> {
                    replaceFragment(FragmentTags.TAG_FRAGMENT_LATER)
                    true
                }
                R.id.nav_menu_collection -> {
                    replaceFragment(FragmentTags.TAG_FRAGMENT_COLLECTION)
                    true
                }
                else ->false
            }
        }
    }

    /*************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*start home fragment*/
        addFragment(FragmentTags.TAG_FRAGMENT_LOTIIE)

        /**/
        bottomNavigation()
    }

    fun start(){
        binding.centralView.visibility = View.VISIBLE
        binding.bottomNav.visibility = View.VISIBLE
        /*start home fragment*/
        replaceFragment(FragmentTags.TAG_FRAGMENT_HOME)
        /**/
        //bottomNavigation()
    }

    /*************************************************************************/
    private fun isFragmentCurrent(tag: String) : Boolean{
        val displayedFragment = supportFragmentManager.fragments.last()
        return displayedFragment.tag == tag
    }

    /*************************************************************************/
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1 ||
            !isFragmentCurrent(FragmentTags.TAG_FRAGMENT_DETAILS)){
            val snackbar = Snackbar.make(
                binding.mainLayout,
                getString(R.string.ask_exit),
                Snackbar.LENGTH_SHORT
            )
            snackbar.setAction(getString(R.string.yes)) {
                finish()
                super.onBackPressed()
            }
            snackbar.show()
        }
        else{
            /*back from details fragment*/
            super.onBackPressed()
        }
    }

    /*************************************************************************/
    //open fragment on film
    fun openFilmDetails(film: Film?){
        val bundle = Bundle()
        bundle.putParcelable(Args.FILM_ARG, film)
        /**/
        replaceFragment(FragmentTags.TAG_FRAGMENT_DETAILS, bundle)
    }

    /*************************************************************************/
    fun getSizeNavMenu() : Int {
        return binding.bottomNav.menu.size
    }
}