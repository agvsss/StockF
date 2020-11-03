package com.proyectofinal.myapplication.objetos

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class Usuario(
    name: String?,
    email: String?,
    favoriteCategorie: String?,
    categorias: MutableList<String>) :

    Parcelable {

    var name: String

    var email: String

    var favoriteCategorie: String

    var categorias: MutableList<String>

    init {
        this.name = name!!
        this.email = email!!
        this.favoriteCategorie = favoriteCategorie!!
        this.categorias = categorias!!
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.createStringArrayList()!!
    )

    constructor() : this ("","","", ArrayList<String>())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(email)
        writeString(favoriteCategorie)
        writeStringList(categorias)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Usuario> = object : Parcelable.Creator<Usuario> {
            override fun createFromParcel(source: Parcel): Usuario = Usuario(source)
            override fun newArray(size: Int): Array<Usuario?> = arrayOfNulls(size)
        }
    }
}