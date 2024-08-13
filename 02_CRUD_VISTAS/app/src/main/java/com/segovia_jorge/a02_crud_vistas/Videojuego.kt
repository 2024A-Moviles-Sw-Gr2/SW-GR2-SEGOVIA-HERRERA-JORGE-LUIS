package com.segovia_jorge.a02_crud_vistas

import android.os.Parcel
import android.os.Parcelable

data class Videojuego(
    var id: Int,
    var nombreJuego: String,
    var generoJuego: String,
    var idEstudio: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombreJuego)
        parcel.writeString(generoJuego)
        parcel.writeInt(idEstudio)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Videojuego> {
        override fun createFromParcel(parcel: Parcel): Videojuego {
            return Videojuego(parcel)
        }

        override fun newArray(size: Int): Array<Videojuego?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Nombre: $nombreJuego Género: $generoJuego"
    }
}