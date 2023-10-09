package com.example.appEcosistemas.ui.main

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.appEcosistemas.R
import com.example.appEcosistemas.data.entity.User
import com.example.appEcosistemas.databinding.ActivityMainBinding
import com.example.appEcosistemas.ui.ecosistema_digital_aumentado.EcosistemaFragment
import com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create.EcosistemaCreateFragment
import com.example.appEcosistemas.ui.login.LoginActivity
import com.example.appEcosistemas.ui.perfil.PerfilFragment
import com.example.appEcosistemas.util.LoadingDialog
import com.example.appEcosistemas.util.ViewCommonFunctions
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    View.OnClickListener, ChangeFragmentListener, MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private var fromOtherActivity = false
    private var userId: String = ""
    private lateinit var presenter: MainPresenter
    private lateinit var loadingDialog: LoadingDialog
    private var keepSplash = true

    companion object {
        const val FLAG_ACTIVITY = "FLAG_ACTIVITY"
        const val USER_ID = "ALUMNO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        presenter = MainPresenter(this, this)
        fromOtherActivity = intent.getBooleanExtra(FLAG_ACTIVITY,   false)


        if (presenter.isLoginAvailable()) {
            presenter.loginTrigger()
        } else if (!fromOtherActivity) {
            goToLogin()
        } else {
            keepSplash = false
            userId = intent.getStringExtra(USER_ID).toString()
            goToEcosistemaFragment(userId)
        }

        initListeners()

    }

    private fun initListeners() {
//        binding.bottomNavMenu.setOnItemSelectedListener(this)
        binding.mainToolbar.tbLogo.setOnClickListener(this)
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToProfileFragment() {
        val perfil = PerfilFragment()
        perfil.listener = this
//        perfil.alumnoID = userId
        replaceFragment(perfil)
        binding.mainToolbar.tbTitle.text = getString(R.string.profile)
    }

    override fun goToEcosistemaFragment() {
        val ecosistemaFragment = EcosistemaFragment()
        ecosistemaFragment.listener = this
        replaceFragment(ecosistemaFragment)
    }

    override fun goToEcosistemaFragment(user: String) {
        val ecosistemaCreateFragment = EcosistemaFragment()
        ecosistemaCreateFragment.listener = this
        ecosistemaCreateFragment.userId = userId
        replaceFragment(ecosistemaCreateFragment)
    }

    override fun goToEcosistemaCreateFragment() {
        val ecosistemaCreateFragment = EcosistemaCreateFragment()
        ecosistemaCreateFragment.listener = this
        replaceFragment(ecosistemaCreateFragment)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tb_logo -> {
                logOut()
            }
        }
    }

    private fun logOut() {
        val positive = DialogInterface.OnClickListener { _, _ ->
            presenter.onLogout()
            goToLogin()
        }

        ViewCommonFunctions.showAlertDialogDesicion(
            this,
            R.string.notice,
            R.string.logout_message,
            R.string.accept,
            R.string.cancel,
            positive,
            ViewCommonFunctions.getCommonCancelDialogListener()
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.im_profile -> {
                goToProfileFragment()
            }
            R.id.im_logout ->{
                logOut()
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName: String = fragment.javaClass.name
        val manager: FragmentManager = supportFragmentManager
        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.fl_container, fragment, backStateName)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            logOut()
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        if(userId.isNotBlank()){
            presenter.saveUserInMemory(userId)
        }
        super.onPause()
    }

    override fun onResume() {
        if (userId.isBlank()) {
            userId = presenter.getUserFromMemoryString()
        }
        super.onResume()
    }

    override fun onLoginSuccess(user: User) {
        keepSplash = false
        this.userId = user.id

    }

    override fun onLoginUnSuccess() {
        onNoLoginData()
    }

    override fun onNoLoginData() {
        keepSplash = false
        val positive =
            DialogInterface.OnClickListener { _, _ -> goToLogin() }

        ViewCommonFunctions.showAlertDialogAccept(
            this,
            R.string.error,
            R.string.no_data_message,
            R.string.accept,
            positive
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