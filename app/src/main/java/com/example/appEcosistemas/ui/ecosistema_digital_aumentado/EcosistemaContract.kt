package com.example.appEcosistemas.ui.ecosistema_digital_aumentado

import com.example.appEcosistemas.data.constants.TipoEcosistema
import com.example.appEcosistemas.data.constants.TipoMarcadores
import com.example.appEcosistemas.data.entity.*
import java.io.File

interface EcosistemaContract {
    interface View {

        fun onGetEcosistemasSuccess(ecosistemas: List<EcosistemaDigitalAumentado>)
        fun onGetEcosistemasUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun getEcosistemasTrigger()
        fun createEcosistemaMarcadorTrigger(
            id : String,
            cantidadUsuarios:Int,
            nombreDelEcosistema:String,
            idAutor:Int,
            descripcion:String,
            tipoEcosistema: TipoEcosistema,
            tipoMarcador : TipoMarcadores,
            marcador : File
        )
        fun createEcosistemaUbicacionTrigger(
            id : String,
            cantidadUsuarios:Int,
            nombreDelEcosistema:String,
            idAutor:Int,
            descripcion:String,
            tipoEcosistema: TipoEcosistema,
            longitud: Int,
            latitud: Int,
            altitud: Int,
            rangoDiametro: Int
        )
    }

    interface Interactor {
        fun getEcosistemas()
    }

    interface InteractorOut {
        fun onGetEcosistemasSuccess(ecosistema: List<EcosistemaDigitalAumentado>)
        fun onGetEcosistemasUnSuccess()
    }
}