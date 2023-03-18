package ru.oleshchuk.module19_kotlin.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oleshchuk.module19_kotlin.data.*
import ru.oleshchuk.module19_kotlin.data.entity.Film
import ru.oleshchuk.module19_kotlin.providers.PreferenceProvider
import ru.oleshchuk.module19_kotlin.utils.FilmsConverter
import ru.oleshchuk.module19_kotlin.utils.toListFilm
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
                    CoroutineScope(Dispatchers.IO).launch {
                        var listFilms = emptyList<Film>()
                        response.body()?.apply {
                            listFilms = tmdbFilms.toListFilm()
                        }
                        //сохраняем в телефон
                        mainRepo.putFilmToCache(listFilms)
                        callback.onSuccess()
                    }
                }

                override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                    CoroutineScope(Dispatchers.IO).launch {
                        //Нет связи
                        callback.onFailure()
                    }
                }

            }
        )
    }

    //читаем базу с телефона
    fun getFilmsFromCache() : List<Film> = mainRepo.getCachedFilms()

    fun getDefCategoryFromPref(): String {
        return preferenceProvider.getDefCategory()
    }

    fun saveDefDefCategoryToPref(category: String){
        preferenceProvider.saveDefCategory(category)
    }
}