package com.example.appEcosistemas.ui.signup

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.appEcosistemas.R
import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.Escuela
import com.example.appEcosistemas.databinding.ActivitySignUpBinding
import com.example.appEcosistemas.ui.login.LoginActivity
import com.example.appEcosistemas.ui.main.MainActivity
import com.example.appEcosistemas.util.LoadingDialog
import com.example.appEcosistemas.util.ViewCommonFunctions

class SignUpActivity : AppCompatActivity(), View.OnClickListener, SignUpContract.View {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var presenter: SignUpPresenter
    private lateinit var loadingDialog: LoadingDialog

    private var escuelas: List<Escuela> = arrayListOf()

    private var grados: List<String> = arrayListOf()

    private var imagen: String = ""

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            if (uriContent != null) {
                imagen = presenter.uriToBase64(uriContent)
                binding.civSignupImage.setImageURI(uriContent)
            } else {
                ViewCommonFunctions.showAlertDialogInformative(
                    this,
                    "Error",
                    "Hubo un error al cargar la imagen."
                )
            }
        } else {
            ViewCommonFunctions.showAlertDialogInformative(
                this,
                "Error",
                "Hubo un problema al cargar la imagen, vuelva a intentarlo"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = SignUpPresenter(this, this)
        loadingDialog = LoadingDialog(this)
        initListeners()
        presenter.getEscuelasTrigger()
    }


    private fun initListeners() {
        binding.signUpToolbar.tbTitle.text = getString(R.string.registro)
        binding.btnCancelSignUp.setOnClickListener(this)
        binding.btnSaveSignUp.setOnClickListener(this)
        binding.civSignupImage.setOnClickListener(this)
        binding.spnSchoolSignUp.adapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_item,
                escuelas
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            }
        binding.spnGradeSignUp.adapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_item,
                grados
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_save_sign_up -> {
                clearAll()
                if (escuelas.isEmpty()) {
                    ViewCommonFunctions.showAlertDialogInformative(
                        this,
                        "Error",
                        "No se lograron cargar las escuelas, intente más tarde"
                    )
                } else if (grados.isEmpty()) {
                    ViewCommonFunctions.showAlertDialogInformative(
                        this,
                        "Error",
                        "No se lograron cargar las escuelas, intente más tarde"
                    )
                } else if (binding.tieSignUpUser.text!!.isEmpty()) {
                    binding.tilSignUpUser.error = "Campo vacio"
                } else if (binding.tieSignUpName.text!!.isEmpty()) {
                    binding.tilSignUpName.error = "Campo vacio"
                } else if (binding.tieSignUpLastnamep.text!!.isEmpty()) {
                    binding.tilSignUpLastnamep.error = "Campo vacio"
                } else if (binding.tieSignUpLastnamem.text!!.isEmpty()) {
                    binding.tilSignUpLastnamem.error = "Campo vacio"
                } else {
                    presenter.createAlumnoTrigger(
                        binding.tieSignUpUser.text.toString(),
                        imagen,
                        binding.tieSignUpName.text.toString(),
                        binding.tieSignUpLastnamep.text.toString(),
                        binding.tieSignUpLastnamem.text.toString(),
                        binding.spnSchoolSignUp.selectedItem.toString(),
                        binding.spnGradeSignUp.selectedItem.toString()
                    )
                }
            }
            R.id.btn_cancel_sign_up -> {
                goToLogin()
                finish()
            }
            R.id.civ_signup_image -> {
                tomarFoto()
            }
        }
    }

    private fun tomarFoto() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )
    }

    private fun clearAll() {
        binding.tilSignUpName.error = null
        binding.tilSignUpLastnamep.error = null
        binding.tilSignUpLastnamem.error = null
        binding.tilSignUpUser.error = null
    }

    private fun goToMain(id: Long) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.FLAG_ACTIVITY, true)
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onGetEscuelasSuccess(escuelas: List<Escuela>) {
        this.escuelas = escuelas

        val gradosCantidad: Int = escuelas[0].grados
        grados = presenter.enumerarGrados(gradosCantidad)
        initRecyclerViews()
    }

    override fun onGetEscuelasUnSuccess() {
        initRecyclerViews()
        ViewCommonFunctions.showAlertDialogInformative(
            this,
            "Error",
            "Hubo un error al obtener las escuelas, intente más tarde"
        )
    }

    private fun initRecyclerViews() {
        binding.spnSchoolSignUp.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                escuelas
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            }
        binding.spnGradeSignUp.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                grados
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            }
    }

    override fun onAlumnoExists() {
        ViewCommonFunctions.showAlertDialogInformative(
            this,
            "Aviso",
            "El alumno con este nombre de usuario ya existe."
        )
    }

    override fun onCreateAlumnoSuccess(alumno: Alumno) {
        val positive =
            DialogInterface.OnClickListener { _, _ ->
                goToMain(alumno.id)
                finish()
            }

        ViewCommonFunctions.showAlertDialogDesicion(
            this,
            R.string.notice,
            R.string.exit_save,
            R.string.accept,
            R.string.cancel,
            positive,
            ViewCommonFunctions.getCommonCancelDialogListener()
        )
    }

    override fun onCreateAlumnoUnSuccess() {
        ViewCommonFunctions.showAlertDialogInformative(
            this,
            "Error",
            "Hubo un error al guardar su información, intente más tarde"
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