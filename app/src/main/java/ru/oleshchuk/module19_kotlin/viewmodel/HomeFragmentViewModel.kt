package ru.oleshchuk.module19_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.oleshchuk.module19_kotlin.App
import ru.oleshchuk.module19_kotlin.domain.Film

class HomeFragmentViewModel : ViewModel() {
    var filmsLivaData = MutableLiveData<List<Film>>()
    private val interactor = App.instance.interactor
    init {
        Log.d("lkLog", "HomeFragmentViewModel postValue")
        filmsLivaData.postValue(interactor.getFilmsDb())
    }
}