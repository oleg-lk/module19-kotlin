package ru.oleshchuk.module19_kotlin.domain

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oleshchuk.module19_kotlin.data.*
import ru.oleshchuk.module19_kotlin.utils.FilmsConverter
import ru.oleshchuk.module19_kotlin.viewmodel.HomeFragmentViewModel

class Interactor(private val mainRepo: MainRepository, private val retrofitService : TmdbApi) {
    //fun getFilmsDb() : List<Film> = mainRepo.films
    fun getFilmsFromApi(page : Int, callback : HomeFragmentViewModel.ApiCallback){
        retrofitService.getFilms(api_key = Keys.KEY_API_TIMDB, language = "ru-RU", page = page).enqueue(
            object :Callback<TmdbResultsDTO>{
                override fun onResponse(
                    call: Call<TmdbResultsDTO>,
                    response: Response<TmdbResultsDTO>
                ) {
                    //Log.d(AppConsts.TAG, "onResponse: ${response.body()?.tmdbFilms?.size}")
                    callback.onSuccess(FilmsConverter.convertTmdbFilmsToFilms(response.body()?.tmdbFilms))
                }

                override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                    //Log.d(AppConsts.TAG, "onFailure: 1")
                }

            }
        )
    }
}