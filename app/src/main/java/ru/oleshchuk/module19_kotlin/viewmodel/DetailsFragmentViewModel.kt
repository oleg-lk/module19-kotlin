package ru.oleshchuk.module19_kotlin.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DetailsFragmentViewModel : ViewModel() {

    suspend fun loadWallpaper(urlWallpaper : String) : Bitmap? {
        return suspendCoroutine {
            val url = URL(urlWallpaper)
            var bitmap : Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                Log.e(TAG, "loadWallpaper: ${e.message}")
            }
            it.resume(bitmap)
        }
    }

    companion object private val TAG = "DetailsFragmentViewModel"
}