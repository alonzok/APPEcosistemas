package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.constants.TipoEcosistema
import com.example.appEcosistemas.data.constants.TipoMarcadores
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class EcosistemaDigitalAumentado(
    var id: String,
    var cantidadUsuarios:Int,
    var nombreDelEcosistema:String,
    var idAutor:String,
    var descripcion:String,
    var tipoEcosistema: TipoEcosistema,
    var tipoDisparador: TipoDisparador,
    var tipoMarcador : TipoMarcadores?,
    var marcador : File?,
    var longitud: Double?,
    var latitud: Double?,
    var altitud: Double?,
    var rangoDiametro: Double?
): Parcelable