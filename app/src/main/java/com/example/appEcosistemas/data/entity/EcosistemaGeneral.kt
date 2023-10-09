package com.example.appEcosistemas.data.entity

import com.example.appEcosistemas.data.constants.TipoEcosistema

abstract class EcosistemaGeneral (
    open var id : String,
    open var cantidadUsuarios:Int,
    open var nombreDelEcosistema:String,
    open var idAutor:Int,
    open var descripcion:String,
    open var tipoEcosistema: TipoEcosistema
    )
