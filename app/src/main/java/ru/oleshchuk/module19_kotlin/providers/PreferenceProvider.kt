package ru.oleshchuk.module19_kotlin.providers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import ru.oleshchuk.module19_kotlin.data.AppConsts

class PreferenceProvider(context: Context) {

    //контекст приложения
    private val _context = context.applicationContext
    //Создаем экземпляр SharedPreferences
    private val prefRef = _context.getSharedPreferences(APP_SETTING, Context.MODE_PRIVATE)

    init{
        //Логика для первого запуска приложения, чтобы положить наши настройки,
        //Сюда потом можно добавить и другие настройки
        if(prefRef.getBoolean(KEY_FIRST_LAUNCH, false)){
            prefRef.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) }
            prefRef.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    fun setCallback(callback: (str: String)->Unit){
        prefRef.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->
            callback(s)
        }
    }

    //Сохраняем категорию
    fun saveDefCategory(category: String){
        prefRef.edit { putString(KEY_DEFAULT_CATEGORY, category)}
    }

    //Забираем категорию
    fun getDefCategory() : String{
        val category = prefRef.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY)
        return category ?: DEFAULT_CATEGORY
    }

    //Ключи для наших настроек,
    companion object{
        private val APP_SETTING = "app setting"
        private val KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH"
        private val KEY_DEFAULT_CATEGORY = "KEY_DEFAULT_CATEGORY"
        private val DEFAULT_CATEGORY = "popular"
    }
}