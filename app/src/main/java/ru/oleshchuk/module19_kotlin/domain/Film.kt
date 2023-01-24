package ru.oleshchuk.module19_kotlin.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val name: String?,
    val posterId: String,
    val desc: String?,
    val rate: Double = 0.0,
    var isFav : Boolean = false ) :
    Parcelable
