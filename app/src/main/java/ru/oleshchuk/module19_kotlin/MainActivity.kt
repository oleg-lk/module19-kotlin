package ru.oleshchuk.module19_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private fun initMenuButtons(){
        val menuButtons : Array<Pair<Int, Int>> = arrayOf(
            Pair(R.id.main_screen_central_menu_button1, R.string.main_screen_central_menu_btn1),
            Pair(R.id.main_screen_central_menu_button2, R.string.main_screen_central_menu_btn2),
            Pair(R.id.main_screen_central_menu_button3, R.string.main_screen_central_menu_btn3),
            Pair(R.id.main_screen_central_menu_button4, R.string.main_screen_central_menu_btn4),
            Pair(R.id.main_screen_central_menu_button5, R.string.main_screen_central_menu_btn5)
        )

        menuButtons.forEach {
                menuButton ->
            findViewById<Button>(menuButton.first).setOnClickListener {
                Toast.makeText(this, menuButton.second, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMenuButtons()
    }
}