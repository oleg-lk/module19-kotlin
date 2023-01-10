package ru.oleshchuk.module19_kotlin.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val name: String?,
    val posterId: Int,
    val desc: String?,
    val rate: Int,
    var isFav : Boolean = false ) :
    Parcelable
