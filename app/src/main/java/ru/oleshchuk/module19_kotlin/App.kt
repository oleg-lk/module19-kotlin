package ru.oleshchuk.module19_kotlin

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.oleshchuk.module19_kotlin.data.ApiConsts
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.domain.Interactor
import java.util.concurrent.TimeUnit

class AppMovie : Application() {
    lateinit var interactor: Interactor
    lateinit var mainRepo : MainRepository

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Инициализируем репозиторий
        mainRepo = MainRepository()
        //Создаём кастомный клиент
        val okHttpClient = OkHttpClient.Builder()
            //Настраиваем таймауты для медленного интернета
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            //Добавляем логгер
            .addInterceptor ( HttpLoggingInterceptor().apply {
                if(BuildConfig.DEBUG){
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            } )
            .build()
        //Создаем Ретрофит
        val retrofit = Retrofit.Builder()
            //Указываем базовый URL из констант
            .baseUrl(ApiConsts.TMDB_URL)
            //Добавляем конвертер
            .addConverterFactory(GsonConverterFactory.create())
            //Добавляем кастомный клиент
            .client(okHttpClient)
            .build()
        //Создаем сам сервис с методами для запросов
        val retrofitService = retrofit.create(TmdbApi::class.java)
        //Инициализируем интерактор
        interactor = Interactor(mainRepo, retrofitService)
    }

    companion object {
        lateinit var instance : AppMovie
            private set
    }
}