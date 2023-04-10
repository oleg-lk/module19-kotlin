package ru.oleshchuk.module19_kotlin.domain

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oleshchuk.module19_kotlin.data.Keys
import ru.oleshchuk.module19_kotlin.data.MainRepository
import ru.oleshchuk.module19_kotlin.data.TmdbApi
import ru.oleshchuk.module19_kotlin.data.TmdbResultsDTO
import ru.oleshchuk.module19_kotlin.data.entity.Film
import ru.oleshchuk.module19_kotlin.providers.PreferenceProvider
import ru.oleshchuk.module19_kotlin.utils.toListFilm
import java.util.concurrent.Executors
import javax.inject.Inject

interface BaseInteractor

class Interactor @Inject constructor(
    private val mainRepo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferenceProvider: PreferenceProvider
) : BaseInteractor {

    private val errorSubject = BehaviorSubject.create<Boolean>()
    fun getErrorSubject() = errorSubject
    fun getFilmsFromApi(page: Int) : Observable<List<Film>> {

        return Observable.create<List<Film>>() { emitter ->
            retrofitService.getFilms(
                category = getDefCategoryFromPref(),
                api_key = Keys.KEY_API_TIMDB, language = "ru-RU", page = page
            ).enqueue(
                //Asynchronously send the request and notify callback of its response
                object : Callback<TmdbResultsDTO> {
                    override fun onResponse(
                        call: Call<TmdbResultsDTO>,
                        response: Response<TmdbResultsDTO>
                    ) {
                        var listFilms = emptyList<Film>()
                        response.body()?.apply {
                            listFilms = tmdbFilms.toListFilm()
                        }
                        //сохраняем в телефон
                        Executors.newSingleThreadExecutor().submit {
                            mainRepo.putFilmToCache(listFilms)
                            emitter.onNext(listFilms)
                        }
                    }
                    override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                        //Нет связи
                        Executors.newSingleThreadExecutor().submit {
                            //читаем базу с телефона
                            emitter.onNext(mainRepo.getCachedFilms())
                            //сигнал об ошибке
                            errorSubject.onNext(true)
                        }
                    }
                }
            )
        }
    }

    fun searchFilmFromApi(queryStr: String) : Observable<List<Film>>{
        val observaleListFilms = retrofitService.searchFilm(
            api_key = Keys.KEY_API_TIMDB, language = "ru-RU", page = 1, query = queryStr
        )
            .subscribeOn(Schedulers.computation())
            .map {
            tmdbDto->tmdbDto.tmdbFilms.toListFilm()
        }
        return observaleListFilms
    }

    fun getDefCategoryFromPref(): String {
        return preferenceProvider.getDefCategory()
    }

    fun saveDefDefCategoryToPref(category: String) {
        preferenceProvider.saveDefCategory(category)
    }
}