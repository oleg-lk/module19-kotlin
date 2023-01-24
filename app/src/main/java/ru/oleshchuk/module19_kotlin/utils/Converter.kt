package ru.oleshchuk.module19_kotlin.utils

import ru.oleshchuk.module19_kotlin.data.TmdbFilm
import ru.oleshchuk.module19_kotlin.domain.Film

object Converter {
    fun convertTmdbFilmsToFilms(tmdbFilms : List<TmdbFilm>) : List<Film>{
        val films = mutableListOf<Film>()
        return films
    }
}