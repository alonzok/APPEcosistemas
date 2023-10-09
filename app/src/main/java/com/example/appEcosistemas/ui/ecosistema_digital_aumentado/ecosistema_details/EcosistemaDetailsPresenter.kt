package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_details

import android.content.Context
import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentadoRequest

class EcosistemaDetailsPresenter(private val view: EcosistemaDetailsContract.View, private val context: Context) : EcosistemaDetailsContract.Presenter,
    EcosistemaDetailsContract.InteractorOut{

    private val interactor = EcosistemaDetailsInteractor(this, context)

    override fun createEcosistemaTrigger(ecosistemaRequest: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador
    ) {
        view.showProgressDialog("Creando Ecosistema...")
        interactor.createEcosistema(ecosistemaRequest, tipoDisparador)
    }

    override fun deleteEcosistemaTrigger(ecosistemaId: String) {
        interactor.deleteEcosistema(ecosistemaId)
    }

    override fun onDeleteEcosistemaSuccess() {
        view.onDeleteEcosistemaSuccess()
    }

    override fun onDeleteEcosistemaUnSuccess() {
        view.onDeleteEcosistemaUnSuccess()
    }

    override fun onCreateEcosistemaSuccess() {
        view.hideProgressDialog()
        view.onCreateEcosistemaSuccess()
    }

    override fun onCreateEcosistemaUnSuccess() {
        view.hideProgressDialog()
        view.onCreateEcosistemaUnSuccess()
    }
}