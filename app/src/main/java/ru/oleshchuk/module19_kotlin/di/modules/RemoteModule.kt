package ru.oleshchuk.module19_kotlin.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.oleshchuk.module19_kotlin.BuildConfig
import ru.oleshchuk.module19_kotlin.data.ApiConsts
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideClient() : OkHttpClient{
        //Создаём кастомный клиент
        val okHttpClient = OkHttpClient.Builder()
            //Настраиваем таймауты для медленного интернета
            .callTimeout(ApiConsts.CALL_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(ApiConsts.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
            //Добавляем логгер
            .addInterceptor ( HttpLoggingInterceptor().apply {
                if(BuildConfig.DEBUG){
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            } )
            .build()
        return okHttpClient
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        //Создаем Ретрофит
        val retrofit = Retrofit.Builder()
            //Указываем базовый URL из констант
            .baseUrl(ApiConsts.TMDB_URL)
            //Добавляем конвертер
            .addConverterFactory(GsonConverterFactory.create())
            //Добавляем кастомный клиент
            .client(okHttpClient)
            .build()
        return retrofit
    }
    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit) : TmdbApi{
        //Создаем сам сервис с методами для запросов
        return retrofit.create(TmdbApi::class.java)
    }
}