package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create

import android.content.Context
import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentadoRequest

class EcosistemaCreatePresenter (private val view: EcosistemaCreateContract.View, private val context: Context) : EcosistemaCreateContract.Presenter,
    EcosistemaCreateContract.InteractorOut{

    private val interactor = EcosistemaCreateInteractor(this, context)

    override fun createEcosistemaTrigger(ecosistemaRequest: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador
    ) {
        view.showProgressDialog("Creando Ecosistema...")
        interactor.createEcosistema(ecosistemaRequest, tipoDisparador)
    }

    override fun onEcosistemaExists() {
//        TODO("Not yet implemented")
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