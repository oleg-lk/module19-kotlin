package ru.oleshchuk.module19_kotlin.di

import dagger.Component
import ru.oleshchuk.module19_kotlin.di.modules.*
import ru.oleshchuk.module19_kotlin.viewmodel.HomeFragmentViewModel
import ru.oleshchuk.module19_kotlin.viewmodel.SettingsFragmentViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DatabaseModule::class,
    DatabaseModuleDao::class,
    DomainModule::class,
    PrefModule::class,
    RemoteModule::class])
interface AppComponent {

    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}