package br.senac.naregua.api

import br.senac.naregua.model.Barbearia
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BarbeariaAPI {

    @GET("/api/barbearia/")
    fun index(): Call<List<Barbearia>>

    @GET("/api/barbearia/{id}")
    fun show(@Path("id") id: Int): Call<List<Barbearia>>


}