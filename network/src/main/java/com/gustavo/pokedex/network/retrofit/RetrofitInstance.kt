package com.gustavo.pokedex.network.retrofit

import com.gustavo.pokedex.network.adapter.NetworkResultAdapterFactory
import com.gustavo.pokedex.network.factory.NetworkEmptyBodyConverterFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createRetrofitInstance(gson: Gson, client: OkHttpClient, baseUrl: String?): Retrofit {
    require(!baseUrl.isNullOrEmpty()) { "Base URL inválida ou não configurada." }
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(NetworkResultAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(NetworkEmptyBodyConverterFactory())
        .client(client)
        .build()
}