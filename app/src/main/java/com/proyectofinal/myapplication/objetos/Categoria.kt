package com.proyectofinal.myapplication.objetos

import android.os.Parcel
import android.os.Parcelable

class Categoria(uid: String?, nombre: String?) : Parcelable {
    var uid: String = uid!!
    var nombre: String = nombre!!

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!
    )

    constructor() : this("","")

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(uid)
        writeString(nombre)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Categoria> = object : Parcelable.Creator<Categoria> {
            override fun createFromParcel(source: Parcel): Categoria = Categoria(source)
            override fun newArray(size: Int): Array<Categoria?> = arrayOfNulls(size)
        }
    }
}