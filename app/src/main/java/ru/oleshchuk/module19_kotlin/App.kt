package ru.oleshchuk.module19_kotlin

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.oleshchuk.module19_kotlin.data.ApiConsts
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.di.AppComponent
import ru.oleshchuk.module19_kotlin.di.DaggerAppComponent
import ru.oleshchuk.module19_kotlin.domain.Interactor
import java.util.concurrent.TimeUnit

class AppMovie : Application() {


    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Создаем компонент
        appComponent = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance : AppMovie
            private set
    }
}