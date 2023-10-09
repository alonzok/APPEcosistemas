package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntidadAumentada (
    var id: String,
    var nombreDelEcosistema:String,
    var idAutor:String,
    var descripcion:String,
    var longitud: Double?,
    var latitud: Double?,
    var altitud: Double?,
    var rangoDiametro: Double?
): Parcelable