package com.example.appEcosistemas.ui.main

import android.content.Context
import com.example.appEcosistemas.data.constants.Constants
import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest
import com.example.appEcosistemas.util.SharedPreferencesConnector

class MainPresenter(private val view: MainContract.View, private val context: Context) : MainContract.Presenter,
    MainContract.InteractorOut {

    private val interactor = MainInteractor(this)
    private val connector = SharedPreferencesConnector.getInstance(context)

    override fun loginTrigger() {
        val username = connector.readString(Constants.Login.USER_LOGIN)
        val password = connector.readString(Constants.Login.PASSWORD_LOGIN)
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            view.onNoLoginData()
        } else {
            val userRequest = UserRequest(username, password)
            interactor.login(userRequest)
        }
    }

    override fun isLoginAvailable(): Boolean {
        val username = connector.readString(Constants.Login.USER_LOGIN)
        val password = connector.readString(Constants.Login.PASSWORD_LOGIN)
        return !(username.isNullOrEmpty() || password.isNullOrEmpty())
    }

    override fun onLoginSuccess(user: User) {
        view.onLoginSuccess(user)
    }

    override fun onLoginUnSuccess() {
        view.onLoginUnSuccess()
    }

    override fun onLogout() {
        connector.clearJSON(context.cacheDir)
    }

    fun saveUserInMemory(id: Long){
        connector.writeLong(Constants.Persistence.USER_PERSISTENCE, id)
    }

    fun saveUserInMemory(id: String){
        connector.writeString(Constants.Persistence.USER_PERSISTENCE, id)
    }

    fun getUserFromMemory(): Long {
        return connector.readLong(Constants.Persistence.USER_PERSISTENCE)
    }

    fun getUserFromMemoryString(): String {
        return connector.readString(Constants.Persistence.USER_PERSISTENCE)!!
    }
}