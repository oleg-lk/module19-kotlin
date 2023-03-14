package ru.oleshchuk.module19_kotlin.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cached_films", indices = [Index(value = ["title"], unique = true)])
data class Film(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val name: String?,
    @ColumnInfo(name = "poster_id") val posterId: String,
    @ColumnInfo(name = "description") val desc: String?,
    @ColumnInfo(name = "vote_average") val rate: Double = 0.0,
    var isFav : Boolean = false ) :
    Parcelable
