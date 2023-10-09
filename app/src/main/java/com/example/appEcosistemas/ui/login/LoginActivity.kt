package com.example.appEcosistemas.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.appEcosistemas.R
import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.data.entity.UserRequest
import com.example.appEcosistemas.databinding.ActivityLoginBinding
import com.example.appEcosistemas.ui.main.MainActivity
import com.example.appEcosistemas.ui.main.MainActivity.Companion.FLAG_ACTIVITY
import com.example.appEcosistemas.ui.main.MainActivity.Companion.USER_ID
import com.example.appEcosistemas.util.LoadingDialog
import com.example.appEcosistemas.util.ViewCommonFunctions

class LoginActivity : AppCompatActivity(), LoginContract.View, View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var presenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        presenter = LoginPresenter(this, this)
        initValues()
    }

    private fun initValues() {
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                binding.tieLoginName.error = null
                binding.passwordEdit.error = null
                if (binding.tieLoginName.text!!.isEmpty()) {
                    binding.tieLoginName.error = "Campo vacio"
                } else if(binding.passwordEdit.text!!.isEmpty()){
                    binding.passwordEdit.error = "Campo vacio"
                } else{
                    val userRequest = UserRequest(binding.tieLoginName.text.toString(), binding.passwordEdit.text.toString());
                    presenter.loginTrigger(userRequest)
                }
            }
        }
    }

    private fun goToMain(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(FLAG_ACTIVITY, true)
        intent.putExtra(USER_ID, user.id)
        startActivity(intent)
    }

    override fun onLoginSuccess(user: User) {
        goToMain(user)
        finish()
    }

    override fun onLoginUnSuccess() {
        ViewCommonFunctions.showAlertDialogInformative(
            this,
            "Error",
            "El usuario no existe o hubo un error en los datos"
        )
    }

    override fun showProgressDialog(message: String) {
        if (loadingDialog.isLoading()) {
            hideProgressDialog()
        }
        loadingDialog.startLoading(message)
    }

    override fun hideProgressDialog() {
        loadingDialog.isDismiss()
    }
}