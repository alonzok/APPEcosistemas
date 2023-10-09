package com.example.appEcosistemas.ui.signup

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.AlumnoRequest
import com.example.appEcosistemas.data.entity.Escuela
import java.io.ByteArrayOutputStream

class SignUpPresenter(private val view: SignUpContract.View, private val context: Context) : SignUpContract.Presenter,
    SignUpContract.InteractorOut {

    private val interactor = SignUpInteractor(this)

    override fun getEscuelasTrigger() {
        view.showProgressDialog("Cargando escuelas...")
        interactor.getEscuelas()
    }

    override fun createAlumnoTrigger(
        usuario: String,
        foto: String,
        nombre: String,
        apellidoP: String,
        apellidoM: String,
        escuela: String,
        grado: String
    ) {
        view.showProgressDialog("Creando cuenta...")
        val alumno = AlumnoRequest(usuario, foto, nombre, apellidoP, apellidoM, escuela, grado, 0, 0, 0)
        interactor.verifyAlumno(alumno)
    }

    override fun onGetEscuelasSuccess(escuelas: List<Escuela>) {
        view.hideProgressDialog()
        view.onGetEscuelasSuccess(escuelas)
    }

    override fun onGetEscuelasUnSuccess() {
        view.hideProgressDialog()
        view.onGetEscuelasUnSuccess()
    }

    override fun onAlumnoExists() {
        view.hideProgressDialog()
        view.onAlumnoExists()
    }

    override fun onCreateAlumnoSuccess(alumno: Alumno) {
        view.hideProgressDialog()
        view.onCreateAlumnoSuccess(alumno)
    }

    override fun onCreateAlumnoUnSuccess() {
        view.hideProgressDialog()
        view.onCreateAlumnoUnSuccess()
    }

    fun enumerarGrados(grados: Int): List<String> {
        val numeros: ArrayList<String> = arrayListOf()
        for (i in 1..grados) {
            numeros.add(toCardinalNumber(i))
        }
        return numeros
    }

    private fun toCardinalNumber(n: Int): String {
        return when (n) {
            1 -> "1ro"
            2 -> "2do"
            3 -> "3ro"
            4 -> "4to"
            5 -> "5to"
            6 -> "6to"
            7 -> "7mo"
            8 -> "8vo"
            9 -> "9no"
            10 -> "10mo"
            11 -> "11vo"
            12 -> "12vo"
            else -> {
                "No se admiten mas de 12"
            }
        }
    }

    fun uriToBase64(uri: Uri): String {
        val imageStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bitByte = baos.toByteArray()
        return Base64.encodeToString(bitByte, Base64.DEFAULT)
    }
}