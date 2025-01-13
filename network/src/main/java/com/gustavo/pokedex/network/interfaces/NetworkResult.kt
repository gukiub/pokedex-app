package com.gustavo.pokedex.network.interfaces

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class FailureWithMessage(val statusCode: Int?, val errorMessage: String?) : NetworkResult<Nothing>()
    data object NetworkError : NetworkResult<Nothing>()
    class GenericException : Exception()
    class ResponseException(message: String?) : Exception(message)
}