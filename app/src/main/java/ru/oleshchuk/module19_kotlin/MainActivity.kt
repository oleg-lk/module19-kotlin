package ru.oleshchuk.module19_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView as BottomNavigationView

class MainActivity : AppCompatActivity() {

    private fun initListeners() {
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_app_bar).setOnMenuItemClickListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
    }
}