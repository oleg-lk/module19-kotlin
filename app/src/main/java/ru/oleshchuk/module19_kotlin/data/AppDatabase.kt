package ru.oleshchuk.module19_kotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.oleshchuk.module19_kotlin.data.dao.FilmDao
import ru.oleshchuk.module19_kotlin.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase() {

    abstract fun getDao() : FilmDao
}