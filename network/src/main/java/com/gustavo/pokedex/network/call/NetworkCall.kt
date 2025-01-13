package com.gustavo.pokedex.network.call

import com.gustavo.pokedex.network.interfaces.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

fun <T> networkCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend () -> NetworkResult<T>,
    onResult: (NetworkResult<T>) -> Unit,
    isLoading: ((Boolean) -> Unit?)? = null
) {
    CoroutineScope(dispatcher).launch {
        withContext(Dispatchers.Main) { isLoading?.invoke(true) }
        try {
            val result = call()
            withContext(Dispatchers.Main) {
                isLoading?.invoke(false)
                onResult(result)
            }
        } catch (e: Exception) {
            val errorResult = if (e is IOException) {
                NetworkResult.NetworkError
            } else {
                NetworkResult.FailureWithMessage(null, e.message)
            }
            withContext(Dispatchers.Main) {
                isLoading?.invoke(false)
                onResult(errorResult)
            }
        }
    }
}