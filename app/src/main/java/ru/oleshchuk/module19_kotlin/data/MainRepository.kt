package ru.oleshchuk.module19_kotlin.data

import ru.oleshchuk.module19_kotlin.data.dao.FilmDao
import ru.oleshchuk.module19_kotlin.data.entity.Film
import java.util.concurrent.Executors
import javax.inject.Inject

interface BaseRepository

class MainRepository @Inject constructor(private val filmDao: FilmDao) : BaseRepository {

    fun putFilmToCache(films: List<Film>) {
        filmDao.insertFilms(films)
    }

    //получить сохраненные  фильмы
    fun getCachedFilms(): List<Film> {
        return filmDao.getCachedFilms()
    }
}