package ru.oleshchuk.module19_kotlin

import android.app.Application
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.domain.Interactor

class App : Application() {
    lateinit var interactor: Interactor
    lateinit var mainRepo : MainRepository

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Инициализируем репозиторий
        mainRepo = MainRepository()
        //Инициализируем интерактор
        interactor = Interactor(mainRepo)
    }

    companion object {
        lateinit var instance : App
            private set
    }
}