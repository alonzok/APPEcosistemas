package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create

import android.content.Context
import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentado
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentadoRequest
import com.example.appEcosistemas.data.manager.DataManager
import com.example.appEcosistemas.util.SharedPreferencesConnector
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class EcosistemaCreateInteractor (private val presenter: EcosistemaCreateContract.InteractorOut, context: Context) :
    EcosistemaCreateContract.Interactor {

    private val dataManager = DataManager()
    private val connector = SharedPreferencesConnector.getInstance(context)

    override fun verifyEcosistema(ecosistema: EcosistemaDigitalAumentadoRequest) {
        TODO("Not yet implemented")
    }

    override fun createEcosistema(ecosistema: EcosistemaDigitalAumentadoRequest, tipoDisparador: TipoDisparador) {
        val observable: Observable<EcosistemaDigitalAumentado> = when(tipoDisparador){
            TipoDisparador.MARCADOR -> {
                dataManager.postEcosistemaMarcador(ecosistema, ecosistema.marcador!!)
            }
            TipoDisparador.UBICACION -> {
                dataManager.postEcosistemaUbicacion(ecosistema)
            }
            TipoDisparador.OPORTUNIDAD -> {
                dataManager.postEcosistemaOportunidad(ecosistema)
            }
        }
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<EcosistemaDigitalAumentado>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onCreateEcosistemaUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: EcosistemaDigitalAumentado?) {
                    if (t != null) {
                        presenter.onCreateEcosistemaSuccess()
                    } else {
                        presenter.onCreateEcosistemaUnSuccess()
                    }
                }

            })
    }
}