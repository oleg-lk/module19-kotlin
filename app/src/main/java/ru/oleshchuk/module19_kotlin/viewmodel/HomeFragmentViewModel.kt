package ru.oleshchuk.module19_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.oleshchuk.module19_kotlin.AppMovie
import ru.oleshchuk.module19_kotlin.data.AppConsts
import ru.oleshchuk.module19_kotlin.domain.Film
import ru.oleshchuk.module19_kotlin.domain.Interactor
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

            override fun onFailure() {}
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}