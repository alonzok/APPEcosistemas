package com.example.appEcosistemas.ui.signup

import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.AlumnoRequest
import com.example.appEcosistemas.data.entity.Escuela
import com.example.appEcosistemas.data.manager.DataManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SignUpInteractor(private val presenter: SignUpContract.InteractorOut) :
    SignUpContract.Interactor {

    private val dataManager = DataManager()

    override fun getEscuelas() {
        val observable = dataManager.getEscuelas()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<Escuela>>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onGetEscuelasUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: List<Escuela>?) {
                    if (!t.isNullOrEmpty()) {
                        presenter.onGetEscuelasSuccess(t)
                    } else {
                        presenter.onGetEscuelasUnSuccess()
                    }
                }

            })
    }

    override fun verifyAlumno(alumno: AlumnoRequest) {
        val observable = dataManager.getAlumnoByUser(alumno.usuario)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<Alumno>>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onCreateAlumnoUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: List<Alumno>?) {
                    if (t.isNullOrEmpty()) {
                        createAlumno(alumno)
                    } else {
                        presenter.onAlumnoExists()
                    }
                }

            })
    }

    override fun createAlumno(alumno: AlumnoRequest) {
        val observable = dataManager.createAlumno(alumno)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Alumno>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onCreateAlumnoUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: Alumno?) {
                    if (t != null) {
                        presenter.onCreateAlumnoSuccess(t)
                    } else {
                        presenter.onCreateAlumnoUnSuccess()
                    }
                }

            })
    }
}