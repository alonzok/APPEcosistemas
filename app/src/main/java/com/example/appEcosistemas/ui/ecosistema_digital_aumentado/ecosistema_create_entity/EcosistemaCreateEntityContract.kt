package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create_entity

import com.example.appEcosistemas.data.entity.*

interface EcosistemaCreateEntityContract {
    interface View {
        fun onEntityExists()
        fun onCreateEntitySuccess()
        fun onCreateEntityUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun createEntityTrigger(entityRequest: EntidadAumentadaRequest)
    }

    interface Interactor {
        fun verifyEntity(entity: EntidadAumentadaRequest)
        fun createEntity(entity: EntidadAumentadaRequest)
    }

    interface InteractorOut {
        fun onEntityExists()
        fun onCreateEntitySuccess()
        fun onCreateEntityUnSuccess()
    }
}