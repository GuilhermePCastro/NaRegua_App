package br.senac.naregua.api

import br.senac.naregua.model.Barbearia
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BarbeariaAPI {

    @GET("/api/barbearia/")
    fun index(): Call<List<Barbearia>>

    @GET("/api/barbearia/{id}")
    fun show(@Path("id") id: Int): Call<List<Barbearia>>

    @GET("/api/filtro/barbearia/")
    fun filtro(@Query("nome") ds_nome: String,
               @Query("cidade") ds_cidade: String,
               @Query("uf") ds_uf: String,
               @Query("bairro") ds_bairro: String): Call<List<Barbearia>>


}