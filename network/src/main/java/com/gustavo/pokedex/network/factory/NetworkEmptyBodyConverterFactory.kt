package com.gustavo.pokedex.network.factory

import com.gustavo.pokedex.network.adapter.NetworkEmptyBodyConverter
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NetworkEmptyBodyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (type == Unit::class.java) {
            NetworkEmptyBodyConverter<Unit>()
        } else {
            null
        }
    }
}
