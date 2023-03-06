package ru.oleshchuk.module19_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.oleshchuk.module19_kotlin.AppMovie
import ru.oleshchuk.module19_kotlin.data.AppConsts
import ru.oleshchuk.module19_kotlin.domain.Film
import ru.oleshchuk.module19_kotlin.domain.Interactor
import java.util.Locale.Category
import javax.inject.Inject

class SettingsFragmentViewModel : ViewModel() {

    val categoryLifeData = MutableLiveData<String>()

    /*get from App*/
    @Inject
    lateinit var interactor : Interactor

    init {
        AppMovie.instance.appComponent.inject(this)

        //Получаем категорию при инициализации, чтобы у нас сразу подтягивалась категория
        getCategory()
    }

    private fun getCategory() {
        //Кладем категорию в LiveData
        categoryLifeData.value = interactor.getDefCategoryFromPref()
    }

    fun putCategory(category: String){
        //Сохраняем в настройки
        interactor.saveDefDefCategoryToPref(category)
        getCategory()
    }
}