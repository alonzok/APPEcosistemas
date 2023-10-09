package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create

import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.entity.*

interface EcosistemaCreateContract {
    interface View {
        fun onEcosistemaExists()
        fun onCreateEcosistemaSuccess()
        fun onCreateEcosistemaUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun createEcosistemaTrigger(ecosistemaRequest: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador)
    }

    interface Interactor {
        fun verifyEcosistema(ecosistema: EcosistemaDigitalAumentadoRequest)
        fun createEcosistema(ecosistema: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador)
    }

    interface InteractorOut {
        fun onEcosistemaExists()
        fun onCreateEcosistemaSuccess()
        fun onCreateEcosistemaUnSuccess()
    }
}