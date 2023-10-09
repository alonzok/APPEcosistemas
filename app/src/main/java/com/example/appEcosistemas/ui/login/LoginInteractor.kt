package com.example.appEcosistemas.ui.login

import android.content.Context
import com.example.appEcosistemas.data.constants.Constants
import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest
import com.example.appEcosistemas.data.manager.DataManager
import com.example.appEcosistemas.util.SharedPreferencesConnector
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginInteractor(private val presenter: LoginContract.InteractorOut, context: Context) :
    LoginContract.Interactor {

    private val dataManager = DataManager()
    private val connector = SharedPreferencesConnector.getInstance(context)

    override fun login(userRequest: UserRequest) {
        val observable = dataManager.login(userRequest)
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
                        connector.writeString(Constants.Login.USER_LOGIN, t.username)
                    } else {
                        presenter.onLoginUnSuccess()
                    }
                }

            })
    }
}