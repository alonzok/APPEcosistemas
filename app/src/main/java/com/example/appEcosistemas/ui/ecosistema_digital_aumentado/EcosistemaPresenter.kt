package com.example.appEcosistemas.ui.ecosistema_digital_aumentado

import android.content.Context
import com.example.appEcosistemas.data.constants.TipoEcosistema
import com.example.appEcosistemas.data.constants.TipoMarcadores
import com.example.appEcosistemas.data.entity.*
import java.io.File

class EcosistemaPresenter(private val view: EcosistemaContract.View, context: Context) :
    EcosistemaContract.Presenter, EcosistemaContract.InteractorOut {

    private val interactor = EcosistemaInteractor(this, context)

    override fun getEcosistemasTrigger() {
        view.showProgressDialog("Obteniendo ecosistemas...")
        interactor.getEcosistemas()
    }

    override fun createEcosistemaMarcadorTrigger(
        id: String,
        cantidadUsuarios: Int,
        nombreDelEcosistema: String,
        idAutor: Int,
        descripcion: String,
        tipoEcosistema: TipoEcosistema,
        tipoMarcador: TipoMarcadores,
        marcador: File
    ) {
        TODO("Not yet implemented")
    }

    override fun createEcosistemaUbicacionTrigger(
        id: String,
        cantidadUsuarios: Int,
        nombreDelEcosistema: String,
        idAutor: Int,
        descripcion: String,
        tipoEcosistema: TipoEcosistema,
        longitud: Int,
        latitud: Int,
        altitud: Int,
        rangoDiametro: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onGetEcosistemasSuccess(ecosistema: List<EcosistemaDigitalAumentado>) {
        view.hideProgressDialog()
        view.onGetEcosistemasSuccess(ecosistema)
    }

    override fun onGetEcosistemasUnSuccess() {
        view.hideProgressDialog()
        view.onGetEcosistemasUnSuccess()
    }

}