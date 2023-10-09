package com.example.appEcosistemas.data.manager

import com.example.appEcosistemas.data.entity.*
import com.example.appEcosistemas.data.remote.api.API
import com.example.appEcosistemas.data.remote.client.ServiceGeneratorSistemaControl
import com.example.appEcosistemas.data.remote.client.ServiceGeneratorSistemaEntidad
import rx.Observable
import java.io.File

class DataManager {

    fun getAlumnoByUser(usuario: String): Observable<List<Alumno>> {
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).getAlumnoByUser(usuario)
    }

    fun getAlumnoByID(id: Long): Observable<Alumno> {
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).getAlumnoByID(id)
    }

    fun getEscuelas(): Observable<List<Escuela>> {
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).getEscuelas()
    }

    fun createAlumno(alumno: AlumnoRequest): Observable<Alumno> {
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).createAlumno(alumno)
    }

    fun editAlumno(alumno: Alumno): Observable<Alumno> {
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).editAlumno(alumno)
    }

    fun login(user: UserRequest): Observable<User>{
        return ServiceGeneratorSistemaControl.createService(API::class.java).login(user)
    }

    fun getAllEcosistema(): Observable<List<EcosistemaDigitalAumentado>>{
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).getAllEcosistema()
    }

    fun deleteEcosistema(ecosistemaId: String): Observable<Boolean>{
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).deleteEcosistema(ecosistemaId)
    }

    fun postEcosistemaMarcador(ecosistemaMarcador: EcosistemaDigitalAumentadoRequest, archivo: File): Observable<EcosistemaDigitalAumentado>{
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).postEcosistemaMarcador(ecosistemaMarcador, archivo)
    }

    fun postEcosistemaUbicacion(ecosistemaRequest: EcosistemaDigitalAumentadoRequest): Observable<EcosistemaDigitalAumentado>{
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).postEcosistemaUbicacion(ecosistemaRequest)
    }

    fun postEcosistemaOportunidad(ecosistemaRequest: EcosistemaDigitalAumentadoRequest): Observable<EcosistemaDigitalAumentado>{
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).postEcosistemaOportunidad(ecosistemaRequest)
    }

    fun postEntity(entidadRequest: EntidadAumentadaRequest): Observable<EntidadAumentada>{
        return ServiceGeneratorSistemaEntidad.createService(API::class.java).postEntidad(entidadRequest)
    }

}