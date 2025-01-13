package com.gustavo.pokedex.network.adapter

import com.gustavo.pokedex.network.call.NetworkResultCall
import com.gustavo.pokedex.network.interfaces.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<NetworkResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> = NetworkResultCall(call)
}
