package ru.oleshchuk.module19_kotlin.domain

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oleshchuk.module19_kotlin.data.Keys
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.data.TmdbResultsDTO
import ru.oleshchuk.module19_kotlin.utils.FilmsConverter
import ru.oleshchuk.module19_kotlin.viewmodel.HomeFragmentViewModel
import javax.inject.Inject

interface BaseInteractor

class Interactor @Inject constructor (private val mainRepo: MainRepository, private val retrofitService : TmdbApi) : BaseInteractor {
    fun getFilmsFromApi(page : Int, callback : HomeFragmentViewModel.ApiCallback){
        retrofitService.getFilms(api_key = Keys.KEY_API_TIMDB, language = "ru-RU", page = page).enqueue(
            object :Callback<TmdbResultsDTO>{
                override fun onResponse(
                    call: Call<TmdbResultsDTO>,
                    response: Response<TmdbResultsDTO>
                ) {
                    callback.onSuccess(FilmsConverter.convertTmdbFilmsToFilms(response.body()?.tmdbFilms))
                }

                override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                }

            }
        )
    }
}