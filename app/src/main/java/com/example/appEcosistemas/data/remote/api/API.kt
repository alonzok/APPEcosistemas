package com.example.appEcosistemas.data.remote.api

import com.example.appEcosistemas.data.entity.*
import retrofit2.http.*
import rx.Observable
import java.io.File

interface API {
    @GET("alumno/getAlumnoPorUsuario")
    fun getAlumno(
        @Query("usuario") usuario: String,
        @Query("escuela") escuela: String
    ): Observable<List<Alumno>>

    @GET("alumno/getAlumnoByUserName")
    fun getAlumnoByUser(
        @Query("usuario") usuario: String
    ): Observable<List<Alumno>>

    @GET("alumno/getAlumnoPorID/{id}")
    fun getAlumnoByID(@Path("id") id: Long): Observable<Alumno>

    @POST("alumno/saveAlumno")
    fun createAlumno(@Body body: AlumnoRequest): Observable<Alumno>

    @POST("alumno/saveAlumno")
    fun editAlumno(@Body body: Alumno): Observable<Alumno>

    @GET("escuela/getEscuelas")
    fun getEscuelas(): Observable<List<Escuela>>

    @POST("login")
    fun login(@Body user: UserRequest):Observable<User>

    @GET("getAllEcosistema")
    fun getAllEcosistema():Observable<List<EcosistemaDigitalAumentado>>

    @DELETE("deleteEcosistema/{id}")
    fun deleteEcosistema(@Path("id") id: String):Observable<Boolean>

//    @POST("postEcosistemaMarcador")
//    fun postEcosistemaMarcador(@Body body: EcosistemaDigitalAumentadoRequest):Observable<EcosistemaDigitalAumentado>

    @Multipart
    @POST("postEcosistemaMarcador")
    fun postEcosistemaMarcador(@Part("ecosistemaMarcador") ecosistemaMarcador: EcosistemaDigitalAumentadoRequest, @Part("archivo") archivo: File):Observable<EcosistemaDigitalAumentado>

    @POST("postEcosistemaUbicacion")
    fun postEcosistemaUbicacion(@Body body: EcosistemaDigitalAumentadoRequest): Observable<EcosistemaDigitalAumentado>

    @POST("postEcosistemaOportunidad")
    fun postEcosistemaOportunidad(@Body body: EcosistemaDigitalAumentadoRequest): Observable<EcosistemaDigitalAumentado>

    @POST("postEntity")
    fun postEntidad(@Body body: EntidadAumentadaRequest): Observable<EntidadAumentada>
}