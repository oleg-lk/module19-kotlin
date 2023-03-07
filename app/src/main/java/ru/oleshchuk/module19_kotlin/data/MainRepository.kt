package ru.oleshchuk.module19_kotlin.data

import android.content.ContentValues
import android.database.Cursor
import ru.oleshchuk.module19_kotlin.data.dao.FilmDao
import ru.oleshchuk.module19_kotlin.db.DatabaseHelper
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

    fun getFilms(): List<Film> {
        return filmDao.getCachedFilms()
    }
}