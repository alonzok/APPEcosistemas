package com.example.appEcosistemas.ui.login

import android.content.Context
import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest

class LoginPresenter(private val view: LoginContract.View, context: Context) :
    LoginContract.Presenter, LoginContract.InteractorOut {
    private val interactor = LoginInteractor(this, context)

    override fun onLoginSuccess(user: User) {
        view.hideProgressDialog()
        view.onLoginSuccess(user)
    }

    override fun onLoginUnSuccess() {
        view.hideProgressDialog()
        view.onLoginUnSuccess()
    }

    override fun loginTrigger(userRequest: UserRequest) {
        view.showProgressDialog("Iniciando sesión...")
        interactor.login(userRequest)
    }

}