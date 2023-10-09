package com.example.appEcosistemas.data.entity

import android.os.Parcelable
import com.example.appEcosistemas.data.constants.TipoEcosistema
import com.example.appEcosistemas.data.constants.TipoMarcadores
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class EcosistemaMarcador(
    var tipoMarcador : TipoMarcadores,
    var marcador : File,
    override var id: String,
    override var cantidadUsuarios: Int,
    override var nombreDelEcosistema: String,
    override var idAutor: Int,
    override var descripcion: String,
    override var tipoEcosistema: TipoEcosistema
) : Parcelable, EcosistemaGeneral(
    id,
    cantidadUsuarios,
    nombreDelEcosistema,
    idAutor,
    descripcion,
    tipoEcosistema
)