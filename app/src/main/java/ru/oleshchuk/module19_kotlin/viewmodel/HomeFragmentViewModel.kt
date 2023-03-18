package ru.oleshchuk.module19_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
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

    //запрос фильмов с сервера или чтени из внутренней бд
    fun getFilms(filmBackFunc: (List<Film>) -> Unit) {

        interactor.getFilmsFromApi(page = 1, callback = object : ApiCallback {
            override fun onSuccess() {
                filmBackFunc(interactor.getFilmsFromCache())
            }

            override fun onFailure() {
                filmBackFunc(interactor.getFilmsFromCache())
                CoroutineScope(Dispatchers.IO).launch {
                    errorChanel.send(true)
                }
            }
        }
        )
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}