package ru.oleshchuk.module19_kotlin.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val name: String?,
    val posterId: Int,
    val desc: String?,
    val isFav : Boolean = false ) :
    Parcelable
