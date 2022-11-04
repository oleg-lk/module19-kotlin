package ru.oleshchuk.module19_kotlin.model

import android.os.Parcel
import android.os.Parcelable

data class Film(val name: String?, val posterId: Int, val desc: String?, val tmHours: Int, val tmMin: Int )
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(posterId)
        parcel.writeString(desc)
        parcel.writeInt(tmHours)
        parcel.writeInt(tmMin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(parcel)
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }
}
