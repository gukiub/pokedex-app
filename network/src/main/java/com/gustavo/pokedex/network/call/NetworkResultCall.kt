package com.gustavo.pokedex.network.call

import android.util.Log
import com.gustavo.pokedex.network.interfaces.NetworkResult
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class NetworkResultCall<T>(proxy: Call<T>) : NetworkCallDelegate<T, NetworkResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<NetworkResult<T>>) = proxy.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            Log.d("NetworkResultCall", "Response code: $code")

            val result: NetworkResult<T> = if (code in 200 until 300) {
                val body = response.body()
                NetworkResult.Success(body)
            } else {
                val errorBody = response.errorBody()?.string()
                when (code) {
                    500 -> {
                        NetworkResult.FailureWithMessage(code, errorBody ?: "Erro interno no servidor (500). Por favor, tente novamente mais tarde.")
                    }
                    400 -> {
                        NetworkResult.FailureWithMessage(code, errorBody ?: "Requisição inválida (400). Verifique os parâmetros.")
                    }
                    else -> {
                        NetworkResult.FailureWithMessage(code, errorBody ?: "Erro inesperado. Código: $code")
                    }
                }
            }

            callback.onResponse(this@NetworkResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("NetworkResultCall", "Request failed with exception: ${t.message}")

            val result = if (t is IOException) {
                NetworkResult.NetworkError
            } else {
                NetworkResult.FailureWithMessage(null, "Falha na requisição: ${t.localizedMessage}")
            }

            callback.onResponse(this@NetworkResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = NetworkResultCall(proxy.clone())

    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}
