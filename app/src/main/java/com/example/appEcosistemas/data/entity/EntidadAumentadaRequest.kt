package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntidadAumentadaRequest (
    var id: String?,
    var nombreDelEntidad: String,
    var idAutor: String,
    var descripcion:String,
    var idEcosistema: String,
    var longitud: Double?,
    var latitud: Double?,
    var altitud: Double?
): Parcelable