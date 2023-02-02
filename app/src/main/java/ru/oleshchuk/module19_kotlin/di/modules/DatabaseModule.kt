package ru.oleshchuk.module19_kotlin.di.modules

import dagger.Module
import dagger.Provides
import ru.oleshchuk.module19_kotlin.data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesRepository() : MainRepository = MainRepository()
}