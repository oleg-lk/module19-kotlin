package ru.oleshchuk.module19_kotlin.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.domain.BaseInteractor
import ru.oleshchuk.module19_kotlin.domain.Interactor
import javax.inject.Singleton

@Module
interface DomainModule {
    @Binds
    @Singleton
    fun bindInteractor(interactor: Interactor) : BaseInteractor
}