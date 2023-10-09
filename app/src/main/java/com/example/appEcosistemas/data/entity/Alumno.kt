package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alumno(
    var id: Long,
    var usuario: String,
    var foto: String,
    var nombre: String,
    var apellidoP: String,
    var apellidoM: String,
    var escuela: String,
    var grado: String,
    var calif1: Int,
    var calif2: Int,
    var calif3: Int
) : Parcelable
