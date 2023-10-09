package com.example.appEcosistemas.data.remote.client

import com.example.appEcosistemas.data.constants.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGeneratorSistemaEntidad {
    private val builder = Retrofit.Builder()
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
        .baseUrl(Constants.API.SISTEMA_ENTIDAD_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().serializeNulls().setDateFormat(Constants.API.TICKET_DATE_FORMAT)
                    .create()
            )
        )

    private val logging = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    fun <T> createService(serviceClass: Class<T>): T {
        if (retrofit == null) {

            if (okHttpClient == null) {

                okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(logging)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()
            }
            retrofit = builder.client(
                okHttpClient!!
            ).build()
        }

        return retrofit!!.create(serviceClass)
    }
}
