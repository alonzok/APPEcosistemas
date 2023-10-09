package com.example.appEcosistemas.ui.perfil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.Escuela
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList
import android.util.Base64
import java.io.ByteArrayInputStream

class PerfilPresenter(private val view: PerfilContract.View, private val context: Context) : PerfilContract.Presenter,
    PerfilContract.InteractorOut {

    private val interactor = PerfilInteractor(this)

    override fun getAlumnoTrigger(id: Long) {
        view.showProgressDialog("Buscando alumno...")
        interactor.getAlumno(id)
    }

    override fun getEscuelasTrigger() {
        view.showProgressDialog("Buscando escuelas...")
        interactor.getEscuelas()
    }

    override fun saveAlumnoTrigger(
        id: Long,
        usuario: String,
        foto: String,
        nombre: String,
        apellidoP: String,
        apellidoM: String,
        escuela: String,
        grado: String,
        calif1: Int,
        calif2: Int,
        calif3: Int
    ) {
        view.showProgressDialog("Guardando datos...")
        val alumno = Alumno(
            id,
            usuario,
            foto,
            nombre,
            apellidoP,
            apellidoM,
            escuela,
            grado,
            calif1,
            calif2,
            calif3
        )
        interactor.saveAlumno(alumno)
    }

    override fun onGetAlumnoSuccess(alumno: Alumno) {
        view.hideProgressDialog()
        view.onGetAlumnoSuccess(alumno)
    }

    override fun onGetAlumnoUnSuccess() {
        view.hideProgressDialog()
        view.onGetAlumnoUnSuccess()
    }

    override fun onGetEscuelasSuccess(escuelas: List<Escuela>) {
        view.hideProgressDialog()
        view.onGetEscuelasSuccess(escuelas)
    }

    override fun onGetEscuelasUnSuccess() {
        view.hideProgressDialog()
        view.onGetEscuelasUnSuccess()
    }

    override fun onsaveAlumnoSuccess(alumno: Alumno) {
        view.hideProgressDialog()
        view.onsaveAlumnoSuccess(alumno)
    }

    override fun onsaveAlumnoUnSuccess() {
        view.hideProgressDialog()
        view.onsaveAlumnoUnSuccess()
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

    fun base64ToBitmap(imagen: String): Bitmap {
        val bitByte = Base64.decode(imagen, Base64.DEFAULT)
        val bais = ByteArrayInputStream(bitByte)
        return BitmapFactory.decodeStream(bais)
    }
}