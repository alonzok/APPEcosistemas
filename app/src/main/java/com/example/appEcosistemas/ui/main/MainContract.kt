package com.example.appEcosistemas.ui.main

import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest

interface MainContract {
    interface View {
        fun onLoginSuccess(user: User)
        fun onLoginUnSuccess()
        fun onNoLoginData()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun loginTrigger()
        fun isLoginAvailable(): Boolean
        fun onLogout()
    }

    interface Interactor {
        fun login(user: UserRequest)
    }

    interface InteractorOut {
        fun onLoginSuccess(user: User)
        fun onLoginUnSuccess()
    }
}