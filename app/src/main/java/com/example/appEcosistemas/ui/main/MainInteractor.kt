package com.example.appEcosistemas.ui.main

import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest
import com.example.appEcosistemas.data.manager.DataManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainInteractor(private val presenter: MainContract.InteractorOut) :
    MainContract.Interactor {

    private val dataManager = DataManager()

    override fun login(user: UserRequest) {
        val observable = dataManager.login(user)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<User>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {
                    presenter.onLoginUnSuccess()
                    e?.printStackTrace()
                }

                override fun onNext(t: User?) {
                    if (t != null) {
                        presenter.onLoginSuccess(t)
                    } else {
                        presenter.onLoginUnSuccess()
                    }
                }

            })
    }
}