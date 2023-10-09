package com.example.appEcosistemas.ui.perfil

import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.Escuela
import com.example.appEcosistemas.data.manager.DataManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PerfilInteractor(private val presenter: PerfilContract.InteractorOut) :
    PerfilContract.Interactor {

    private val dataManager = DataManager()

    override fun getAlumno(id: Long) {
        val observable = dataManager.getAlumnoByID(id)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Alumno>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onGetAlumnoUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: Alumno?) {
                    if (t != null) {
                        presenter.onGetAlumnoSuccess(t)
                    } else {
                        presenter.onGetEscuelasUnSuccess()
                    }
                }

            })
    }

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

    override fun saveAlumno(alumno: Alumno) {
        val observable = dataManager.editAlumno(alumno)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Alumno>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onsaveAlumnoUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: Alumno?) {
                    if (t != null) {
                        presenter.onsaveAlumnoSuccess(t)
                    } else {
                        presenter.onsaveAlumnoUnSuccess()
                    }
                }

            })
    }
}