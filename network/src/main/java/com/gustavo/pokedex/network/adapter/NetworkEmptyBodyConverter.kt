package com.gustavo.pokedex.network.adapter

import okhttp3.ResponseBody
import retrofit2.Converter

class NetworkEmptyBodyConverter<T> : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        value.close()
        return null
    }
}