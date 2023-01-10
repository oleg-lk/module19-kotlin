package ru.oleshchuk.module19_kotlin.view.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.oleshchuk.module19_kotlin.domain.Film

class FilmDiff(private val oldFilms: List<Film>, private val newFilms : List<Film>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldFilms.size
    }

    override fun getNewListSize(): Int {
        return newFilms.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldFilms[oldItemPosition].posterId == newFilms[newItemPosition].posterId)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFilm = oldFilms[oldItemPosition]
        val newFilm = newFilms[newItemPosition]
        return oldFilm.desc == newFilm.desc &&
               oldFilm.name == newFilm.name &&
               oldFilm.posterId == newFilm.posterId
    }
}