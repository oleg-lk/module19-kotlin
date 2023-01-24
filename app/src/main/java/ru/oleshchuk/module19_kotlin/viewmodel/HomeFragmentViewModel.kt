package ru.oleshchuk.module19_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.oleshchuk.module19_kotlin.AppMovie
import ru.oleshchuk.module19_kotlin.domain.Film

class HomeFragmentViewModel : ViewModel() {
    var filmsLivaData = MutableLiveData<List<Film>>()

    /*get from App*/
    private val interactor = AppMovie.instance.interactor

    init {
        //Log.d("lkLog", "HomeFragmentViewModel postValue")
        //filmsLivaData.postValue(interactor.getFilmsDb())
        interactor.getFilmsFromApi(page = 1, callback = object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsLivaData.postValue(films)
            }

            override fun onFailure() {

            }

        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}