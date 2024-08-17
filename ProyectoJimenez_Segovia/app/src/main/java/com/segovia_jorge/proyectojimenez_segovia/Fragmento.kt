package com.segovia_jorge.proyectojimenez_segovia

import android.os.Parcel
import android.os.Parcelable

data class Fragmento(
    val imageResId: Int,
    val title: String,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageResId)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Fragmento> {
        override fun createFromParcel(parcel: Parcel): Fragmento = Fragmento(parcel)
        override fun newArray(size: Int): Array<Fragmento?> = arrayOfNulls(size)
    }
}
