package ru.oleshchuk.module19_kotlin.di.modules

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.oleshchuk.module19_kotlin.data.AppDatabase
import ru.oleshchuk.module19_kotlin.data.BaseRepository
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.dao.FilmDao
import ru.oleshchuk.module19_kotlin.db.DatabaseHelper
import javax.inject.Singleton

@Module
class DatabaseModuleDao {
    @Provides
    @Singleton
    fun provideFilmDao(context: Context) : FilmDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, "film_db")
            .build().getDao()
    }
}

@Module
interface DatabaseModule {
    @Binds
    @Singleton
    fun bindRepository(repository: MainRepository) : BaseRepository

    @Binds
    @Singleton
    fun bindHelper(helper: DatabaseHelper) : SQLiteOpenHelper
}