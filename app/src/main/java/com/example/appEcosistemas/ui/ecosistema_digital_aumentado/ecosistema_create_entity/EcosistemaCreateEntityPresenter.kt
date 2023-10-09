package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create_entity

import android.content.Context
import com.example.appEcosistemas.data.entity.EntidadAumentadaRequest

class EcosistemaCreateEntityPresenter (private val view: EcosistemaCreateEntityContract.View, private val context: Context) : EcosistemaCreateEntityContract.Presenter,
    EcosistemaCreateEntityContract.InteractorOut{

    private val interactor = EcosistemaCreateEntityInteractor(this, context)

    override fun createEntityTrigger(entityRequest: EntidadAumentadaRequest) {
        view.showProgressDialog("Creando entidad...")
        interactor.createEntity(entityRequest)
    }

    override fun onEntityExists() {
//        TODO("Not yet implemented")
    }

    override fun onCreateEntitySuccess() {
        view.hideProgressDialog()
        view.onCreateEntitySuccess()
    }

    override fun onCreateEntityUnSuccess() {
        view.hideProgressDialog()
        view.onCreateEntityUnSuccess()
    }
}