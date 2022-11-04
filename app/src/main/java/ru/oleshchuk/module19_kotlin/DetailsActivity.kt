package ru.oleshchuk.module19_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.oleshchuk.module19_kotlin.databinding.ActivityDetailsBinding
import ru.oleshchuk.module19_kotlin.model.Film

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        val film = intent.extras?.get("film") as Film
        if (film != null)        {
            binding.detailsToolbar.title = film.name
            binding.centralPoster.setImageResource(film.posterId)
            binding.detailsDesc.text = film.desc
        }
        setContentView(binding.root)
    }
}