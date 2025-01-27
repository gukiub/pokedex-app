package com.gustavo.pokedex.network

import android.util.Log
import com.gustavo.pokedex.model.PokemonApiResult
import com.gustavo.pokedex.model.PokemonsApiResult
import com.gustavo.pokedex.network.call.networkCall
import com.gustavo.pokedex.network.interfaces.NetworkResult
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val service: PokemonService
) : PokemonServiceImpl {
    companion object {
        private const val TAG = "PokemonRepository"
    }

    override fun listPokemons(
        limit: Int,
        onResult: (PokemonsApiResult?) -> Unit,
        onError: (Throwable) -> Unit,
        isLoading: (Boolean) -> Unit
    ) {
        networkCall(dispatcher = Dispatchers.Default, call = {
            service.listPokemons(limit)
        }, onResult = { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "api fetched successfully: $result")
                    onResult(result.data)
                }
                is NetworkResult.FailureWithMessage -> {
                    Log.e(TAG, "api fetch failed: $result")
                    onError(Exception(result.errorMessage))
                }
                is NetworkResult.NetworkError -> {
                    Log.e(TAG, "api fetch failed: $result")
                    onError(IOException("Erro de rede"))
                }
                else -> {
                    Log.w(TAG, "unknown error: $result")
                    onError(Exception("Erro desconhecido"))
                }
            }
        }, isLoading = isLoading)
    }

    override fun getPokemon(
        number: Int,
        onResult: (PokemonApiResult?) -> Unit,
        onError: (Throwable) -> Unit,
        isLoading: (Boolean) -> Unit
    ) {
        networkCall(dispatcher = Dispatchers.Default, call = {
            service.getPokemon(number)
        }, onResult = { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "api fetched successfully: $result")
                    onResult(result.data)
                }
                is NetworkResult.FailureWithMessage -> {
                    Log.e(TAG, "api fetch failed: $result")
                    onError(Exception(result.errorMessage))
                }
                is NetworkResult.NetworkError -> {
                    Log.e(TAG, "api fetch failed: $result")
                    onError(IOException("Erro de rede"))
                }
                else -> {
                    Log.w(TAG, "unknown error: $result")
                    onError(Exception("Erro desconhecido"))
                }
            }
        }, isLoading = isLoading)
    }
}