package com.example.viacep

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Address(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val localidade: String
)

interface ViaCepApi {
    @GET("{cep}/json/")
    suspend fun getAddress(@Path("cep") cep: String): Address?
}

object ViaCepService {
    private val api = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ViaCepApi::class.java)

    suspend fun getAddress(cep: String): Address? = api.getAddress(cep)
}
