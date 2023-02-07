package ru.oleshchuk.module19_kotlin.di.modules

import dagger.Binds
import dagger.Module
import ru.oleshchuk.module19_kotlin.data.BaseRepository
import ru.oleshchuk.module19_kotlin.data.MainRepository
import javax.inject.Singleton

@Module
interface DatabaseModule {
    @Binds
    @Singleton
    fun bindRepository(repo: MainRepository) : BaseRepository
}