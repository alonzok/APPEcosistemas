package com.example.appEcosistemas.ui.perfil

import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.Escuela

interface PerfilContract {
    interface View {
        fun onGetAlumnoSuccess(alumno: Alumno)
        fun onGetAlumnoUnSuccess()
        fun onGetEscuelasSuccess(escuelas: List<Escuela>)
        fun onGetEscuelasUnSuccess()
        fun onsaveAlumnoSuccess(alumno: Alumno)
        fun onsaveAlumnoUnSuccess()
        fun showProgressDialog(message: String)
        fun hideProgressDialog()
    }

    interface Presenter {
        fun getAlumnoTrigger(id: Long)
        fun getEscuelasTrigger()
        fun saveAlumnoTrigger(
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
        )
    }

    interface Interactor {
        fun getAlumno(id: Long)
        fun getEscuelas()
        fun saveAlumno(alumno: Alumno)
    }

    interface InteractorOut {
        fun onGetAlumnoSuccess(alumno: Alumno)
        fun onGetAlumnoUnSuccess()
        fun onGetEscuelasSuccess(escuelas: List<Escuela>)
        fun onGetEscuelasUnSuccess()
        fun onsaveAlumnoSuccess(alumno: Alumno)
        fun onsaveAlumnoUnSuccess()
    }
}