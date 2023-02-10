package ru.oleshchuk.module19_kotlin.domain

import android.R
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oleshchuk.module19_kotlin.data.*
import ru.oleshchuk.module19_kotlin.providers.PreferenceProvider
import ru.oleshchuk.module19_kotlin.utils.FilmsConverter
import ru.oleshchuk.module19_kotlin.viewmodel.HomeFragmentViewModel
import javax.inject.Inject

interface BaseInteractor

class Interactor @Inject constructor (
    private val mainRepo: MainRepository,
    private val retrofitService : TmdbApi,
    private val preferenceProvider: PreferenceProvider) : BaseInteractor {
    fun getFilmsFromApi(page : Int, callback : HomeFragmentViewModel.ApiCallback){
        retrofitService.getFilms(
            category = getDefCategoryFromPref(),
            api_key = Keys.KEY_API_TIMDB, language = "ru-RU", page = page).enqueue(
            object :Callback<TmdbResultsDTO>{
                override fun onResponse(
                    call: Call<TmdbResultsDTO>,
                    response: Response<TmdbResultsDTO>
                ) {
                    callback.onSuccess(FilmsConverter.convertTmdbFilmsToFilms(response.body()?.tmdbFilms))
                }

                override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                    callback.onFailure()
                }

            }
        )
    }

    fun setPrefCallback( callback: (str:String)->Unit){
        preferenceProvider.setCallback(callback)
    }

    fun getDefCategoryFromPref(): String {
        return preferenceProvider.getDefCategory()
    }

    fun saveDefDefCategoryToPref(category: String){
        preferenceProvider.saveDefCategory(category)
    }
}