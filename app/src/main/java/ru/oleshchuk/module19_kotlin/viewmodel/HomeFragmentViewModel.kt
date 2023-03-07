package ru.oleshchuk.module19_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.oleshchuk.module19_kotlin.AppMovie
import ru.oleshchuk.module19_kotlin.data.entity.Film
import ru.oleshchuk.module19_kotlin.domain.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    var filmsLivaData = MutableLiveData<List<Film>>()

    /*get from App*/
    @Inject
    lateinit var interactor : Interactor

    init {
        AppMovie.instance.appComponent.inject(this)
        getFilms()
    }
    fun getFilms() {
        interactor.getFilmsFromApi(page = 1, callback = object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsLivaData.postValue(films)
            }

            override fun onFailure() {
                Executors.newSingleThreadExecutor().execute {
                    filmsLivaData.postValue(interactor.getFilmsFromDB())
                }
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}