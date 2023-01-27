package ru.oleshchuk.module19_kotlin.utils

import ru.oleshchuk.module19_kotlin.data.TmdbFilm
import ru.oleshchuk.module19_kotlin.domain.Film

object FilmsConverter {
    fun convertTmdbFilmsToFilms(tmdbFilms : List<TmdbFilm>?) : List<Film>{
        var films = emptyList<Film>()
        tmdbFilms?.let {
            films = List<Film>(tmdbFilms.size){
                val tmdbFilm = tmdbFilms[it]
                Film(name = tmdbFilm.title,
                    posterId = tmdbFilm.poster_path,
                    desc = tmdbFilm.overview,
                    rate = tmdbFilm.vote_average, isFav = false)
            }
        }
        return films
    }
}