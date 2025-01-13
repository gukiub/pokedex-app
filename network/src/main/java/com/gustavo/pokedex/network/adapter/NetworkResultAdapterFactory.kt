package com.gustavo.pokedex.network.adapter

import com.gustavo.pokedex.network.interfaces.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                NetworkResult::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    NetworkResultAdapter(resultType)
                }

                else -> null
            }
        }

        else -> null
    }
}