package ru.oleshchuk.module19_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.channels.Channel
import ru.oleshchuk.module19_kotlin.AppMovie
import ru.oleshchuk.module19_kotlin.data.entity.Film
import ru.oleshchuk.module19_kotlin.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {

    val errorChanel = Channel<Boolean>(Channel.CONFLATED)

    /*get from App*/
    @Inject
    lateinit var interactor: Interactor

    init {
        AppMovie.instance.appComponent.inject(this)
    }

    fun getError() = interactor.getErrorSubject()

    //запрос фильмов с сервера или чтени из внутренней бд
    fun getFilms() : Observable<List<Film>> {
        return interactor.getFilmsFromApi(page = 1)
    }

    fun searchFilmFromApi(queryStr: String) : Observable<List<Film>>{
        return interactor.searchFilmFromApi(queryStr)
    }
}