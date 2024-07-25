package com.segovia_jorge.a02_crud_vistas

import android.os.Parcel
import android.os.Parcelable

data class EstudioVideojuego(
    var id: Int,
    var nombreEstudio: String,
    var empleadosEstudio: Int,
    var paisEstudio: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombreEstudio)
        parcel.writeInt(empleadosEstudio)
        parcel.writeString(paisEstudio)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<EstudioVideojuego> {
        override fun createFromParcel(parcel: Parcel): EstudioVideojuego {
            return EstudioVideojuego(parcel)
        }

        override fun newArray(size: Int): Array<EstudioVideojuego?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Nombre: $nombreEstudio Número de empleados: $empleadosEstudio"
    }
}
