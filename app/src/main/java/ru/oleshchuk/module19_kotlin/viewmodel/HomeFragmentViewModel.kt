package ru.oleshchuk.module19_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.oleshchuk.module19_kotlin.AppMovie
import ru.oleshchuk.module19_kotlin.data.entity.Film
import ru.oleshchuk.module19_kotlin.domain.Interactor
import ru.oleshchuk.module19_kotlin.utils.SingleLiveEvent
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val liveFilmsData : LiveData<List<Film>>
    val liveShowProgressBar = MutableLiveData<Boolean>()
    val liveShowError = SingleLiveEvent<String>()

    /*get from App*/
    @Inject
    lateinit var interactor : Interactor

    init {
        AppMovie.instance.appComponent.inject(this)
        liveFilmsData = interactor.getFilmsFromDB()
        getFilms()
    }
    fun getFilms() {
        liveShowProgressBar.postValue(true)
        interactor.getFilmsFromApi(page = 1, callback = object : ApiCallback {
            override fun onSuccess() {
                liveShowProgressBar.postValue(false)
            }

            override fun onFailure() {
                liveShowProgressBar.postValue(false)
                liveShowError.postValue("Fail")
            }
        })
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}