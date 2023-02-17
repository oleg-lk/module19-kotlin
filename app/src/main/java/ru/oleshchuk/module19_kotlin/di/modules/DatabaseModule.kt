package ru.oleshchuk.module19_kotlin.di.modules

import android.database.sqlite.SQLiteOpenHelper
import dagger.Binds
import dagger.Module
import ru.oleshchuk.module19_kotlin.data.BaseRepository
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.db.DatabaseHelper
import javax.inject.Singleton

@Module
interface DatabaseModule {
    @Binds
    @Singleton
    fun bindRepository(repo: MainRepository) : BaseRepository

    @Binds
    @Singleton
    fun bindHelper(helper: DatabaseHelper) : SQLiteOpenHelper
}