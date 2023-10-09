package com.example.appEcosistemas.ui.login

import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest

interface LoginContract {
    interface View {
        fun onLoginSuccess(user: User)
        fun onLoginUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun loginTrigger(userRequest: UserRequest)
    }

    interface Interactor {
        fun login(userRequest: UserRequest)
    }

    interface InteractorOut {
        fun onLoginSuccess(user: User)
        fun onLoginUnSuccess()
    }
}