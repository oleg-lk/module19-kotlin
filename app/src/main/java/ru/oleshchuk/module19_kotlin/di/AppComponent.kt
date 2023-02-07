package ru.oleshchuk.module19_kotlin.di

import dagger.Component
import ru.oleshchuk.module19_kotlin.di.modules.DatabaseModule
import ru.oleshchuk.module19_kotlin.di.modules.DomainModule
import ru.oleshchuk.module19_kotlin.di.modules.RemoteModule
import ru.oleshchuk.module19_kotlin.viewmodel.HomeFragmentViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class,DomainModule::class,RemoteModule::class])
interface AppComponent {

    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}