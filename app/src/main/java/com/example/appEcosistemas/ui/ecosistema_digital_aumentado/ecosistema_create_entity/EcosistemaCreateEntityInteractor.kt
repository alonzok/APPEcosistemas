package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create_entity

import android.content.Context
import com.example.appEcosistemas.data.entity.EntidadAumentada
import com.example.appEcosistemas.data.entity.EntidadAumentadaRequest
import com.example.appEcosistemas.data.manager.DataManager
import com.example.appEcosistemas.util.SharedPreferencesConnector
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class EcosistemaCreateEntityInteractor (private val presenter: EcosistemaCreateEntityContract.InteractorOut, context: Context) :
    EcosistemaCreateEntityContract.Interactor {

    private val dataManager = DataManager()
    private val connector = SharedPreferencesConnector.getInstance(context)

    override fun verifyEntity(entity: EntidadAumentadaRequest) {
        TODO("Not yet implemented")
    }

    override fun createEntity(entity: EntidadAumentadaRequest) {
        val observable: Observable<EntidadAumentada> = dataManager.postEntity(entity)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<EntidadAumentada>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onCreateEntityUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: EntidadAumentada?) {
                    if (t != null) {
                        presenter.onCreateEntitySuccess()
                    } else {
                        presenter.onCreateEntityUnSuccess()
                    }
                }

            })
    }
}