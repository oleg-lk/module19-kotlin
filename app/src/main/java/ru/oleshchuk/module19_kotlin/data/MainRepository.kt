package ru.oleshchuk.module19_kotlin.data

import android.content.ContentValues
import android.database.Cursor
import ru.oleshchuk.module19_kotlin.db.DatabaseHelper
import ru.oleshchuk.module19_kotlin.domain.Film
import javax.inject.Inject

interface BaseRepository

class MainRepository @Inject constructor(databaseHelper: DatabaseHelper) : BaseRepository {
    //Инициализируем объект для взаимодействия с БД
    private val sqlDb = databaseHelper.readableDatabase
    private lateinit var cursor: Cursor

    fun putFilmToDb(film: Film) {
        //Создаем объект, который будет хранить пары ключ-значение, для того
        //чтобы класть нужные данные в нужные столбцы
        val cv = ContentValues().apply {
            put("${DatabaseHelper.COLUMN_TITLE}", film.name)
            put("${DatabaseHelper.COLUMN_POSTER}", film.posterId)
            put("${DatabaseHelper.COLUMN_DESCRIPTION}", film.desc)
            put("${DatabaseHelper.COLUMN_RATING}", film.rate)
        }
        //Кладем фильм в БД
        sqlDb.insert("${DatabaseHelper.TABLE_NAME}", null, cv)
    }

    fun getFilms(): List<Film> {
        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        var resFilms = ArrayList<Film>()
        cursor.use {
            if (it.moveToFirst()) {
                //Итерируемся по таблице, пока есть записи, и создаем на основании объект Film
                do {
                    val title = DatabaseHelper.getTitle(it)
                    val poster = DatabaseHelper.getPoster(it)
                    val description = DatabaseHelper.getDescription(it)
                    val rating = DatabaseHelper.getRating(it)
                    val film = Film(name = title, posterId = poster, desc = description,
                        rate = rating
                    )
                    resFilms.add(film)
                } while (it.moveToNext())
            }
        }
        return resFilms
    }
}