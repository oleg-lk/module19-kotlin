package ru.oleshchuk.module19_kotlin.di.modules

import dagger.Module
import dagger.Provides
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.domain.Interactor
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideInteractor(mainRepo : MainRepository, retrofitService : TmdbApi) : Interactor{
        //Инициализируем интерактор
        return Interactor(mainRepo, retrofitService)
    }
}