package com.example.appEcosistemas.ui.signup

import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.AlumnoRequest
import com.example.appEcosistemas.data.entity.Escuela

interface SignUpContract {
    interface View {
        fun onGetEscuelasSuccess(escuelas: List<Escuela>)
        fun onGetEscuelasUnSuccess()
        fun onAlumnoExists()
        fun onCreateAlumnoSuccess(alumno: Alumno)
        fun onCreateAlumnoUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun getEscuelasTrigger()
        fun createAlumnoTrigger(
            usuario: String,
            foto: String,
            nombre: String,
            apellidoP: String,
            apellidoM: String,
            escuela: String,
            grado: String
        )
    }

    interface Interactor {
        fun getEscuelas()
        fun verifyAlumno(alumno: AlumnoRequest)
        fun createAlumno(alumno: AlumnoRequest)
    }

    interface InteractorOut {
        fun onGetEscuelasSuccess(escuelas: List<Escuela>)
        fun onGetEscuelasUnSuccess()
        fun onAlumnoExists()
        fun onCreateAlumnoSuccess(alumno: Alumno)
        fun onCreateAlumnoUnSuccess()
    }
}