package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Escuela(
    var nombre: String,
    var grados: Int,
) : Parcelable {
    override fun toString(): String {
        return nombre
    }
}
