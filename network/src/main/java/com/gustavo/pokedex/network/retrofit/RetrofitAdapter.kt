package com.gustavo.pokedex.network.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun createRetrofitAdapter(gson: Gson, okHttpClient: OkHttpClient, baseUrl: String?): Retrofit {
    return createRetrofitInstance(gson, okHttpClient, baseUrl)
}
