package com.example.appEcosistemas.ui.ecosistema_digital_aumentado

import android.content.Context
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentado
import com.example.appEcosistemas.data.manager.DataManager
import com.example.appEcosistemas.util.SharedPreferencesConnector
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class EcosistemaInteractor(private val presenter: EcosistemaContract.InteractorOut, context: Context) :
    EcosistemaContract.Interactor {

    private val dataManager = DataManager()
    private val connector = SharedPreferencesConnector.getInstance(context)

    override fun getEcosistemas() {
        val observable = dataManager.getAllEcosistema()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<EcosistemaDigitalAumentado>>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onGetEcosistemasUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: List<EcosistemaDigitalAumentado>?) {
                    if (!t.isNullOrEmpty()) {
                        presenter.onGetEcosistemasSuccess(t)
                    } else {

                        presenter.onGetEcosistemasUnSuccess()
                    }
                }

            })
    }


}