package ru.oleshchuk.module19_kotlin.di.modules

import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.domain.BaseInteractor
import ru.oleshchuk.module19_kotlin.domain.Interactor
import ru.oleshchuk.module19_kotlin.providers.PreferenceProvider
import javax.inject.Singleton

@Module
interface DomainModule {
    @Binds
    @Singleton
    fun bindInteractor(interactor: Interactor) : BaseInteractor
}

@Module
class PrefModule(val context: Context){

    @Provides
    fun provideContext() = context

    @Provides
    @Singleton
    //Создаем экземпляр SharedPreferences
    fun providePreferenceProvider(context: Context) = PreferenceProvider(context)
}