package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actividad(
    var id: Int,
    var imagen: Int,
    var nombre: String,
    var instrucciones: String,
    var puntos: Int
) : Parcelable