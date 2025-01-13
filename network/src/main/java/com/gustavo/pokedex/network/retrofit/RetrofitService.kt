package com.gustavo.pokedex.network.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient

fun <S> createRetrofitService(
    gson: Gson,
    okHttpClient: OkHttpClient,
    serviceClass: Class<S>,
    baseUrl: String?
): S {
    require(!baseUrl.isNullOrEmpty()) { "Base URL inválida ou não configurada." }
    val retrofit = createRetrofitAdapter(
        gson,
        okHttpClient,
        baseUrl
    )
    return retrofit.create(serviceClass)
}