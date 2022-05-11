package br.senac.naregua.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class API(val context: Context){

    private val baseUrl = "https://naregua-app-vtnmx.ondigitalocean.app"
    private val timeout = 10000L

    private val retrofit: Retrofit
        get(){

            var okHttp: OkHttpClient


            okHttp = OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val newRequest: Request = chain.request().newBuilder()
                        .build()
                    chain.proceed(newRequest)
                }
                .build()


            return Retrofit.Builder()
                .client(okHttp)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


    val barbearia: BarbeariaAPI
        get(){
            return retrofit.create(BarbeariaAPI::class.java)
        }
}