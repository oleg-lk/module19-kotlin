package ru.oleshchuk.module19_kotlin.data

import androidx.lifecycle.LiveData
import ru.oleshchuk.module19_kotlin.data.dao.FilmDao
import ru.oleshchuk.module19_kotlin.data.entity.Film
import java.util.concurrent.Executors
import javax.inject.Inject

interface BaseRepository

class MainRepository @Inject constructor(private val filmDao: FilmDao) : BaseRepository {

    fun putFilmToDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertFilms(films)
        }
    }

    fun getFilms(): LiveData<List<Film>> {
        return filmDao.getCachedFilms()
    }
}