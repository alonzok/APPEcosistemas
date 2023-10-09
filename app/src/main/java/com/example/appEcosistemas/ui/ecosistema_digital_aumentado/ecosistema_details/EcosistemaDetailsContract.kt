package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_details

import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentadoRequest

interface EcosistemaDetailsContract {
    interface View {
        fun onCreateEcosistemaSuccess()
        fun onCreateEcosistemaUnSuccess()
        fun onDeleteEcosistemaSuccess()
        fun onDeleteEcosistemaUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun createEcosistemaTrigger(ecosistemaRequest: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador)
        fun deleteEcosistemaTrigger(ecosistemaId: String)
    }

    interface Interactor {
        fun verifyEcosistema(ecosistema: EcosistemaDigitalAumentadoRequest)
        fun createEcosistema(ecosistema: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador)
        fun deleteEcosistema(ecosistemaId: String)
    }

    interface InteractorOut {
        fun onCreateEcosistemaSuccess()
        fun onCreateEcosistemaUnSuccess()
        fun onDeleteEcosistemaSuccess()
        fun onDeleteEcosistemaUnSuccess()
    }
}