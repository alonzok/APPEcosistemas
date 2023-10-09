package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import com.example.appEcosistemas.data.constants.TipoEcosistema
import kotlinx.parcelize.Parcelize

@Parcelize
data class EcosistemaUbicacion(
    override var id : String,
    override var cantidadUsuarios:Int,
    override var nombreDelEcosistema:String,
    override var idAutor:Int,
    override var descripcion:String,
    override var tipoEcosistema: TipoEcosistema,
    var longitud: Int,
    var latitud: Int,
    var altitud: Int,
    var rangoDiametro: Int
) : Parcelable, EcosistemaGeneral(
    id,
    cantidadUsuarios,
    nombreDelEcosistema,
    idAutor,
    descripcion,
    tipoEcosistema
)