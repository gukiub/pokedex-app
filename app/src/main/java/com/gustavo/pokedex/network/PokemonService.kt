package com.gustavo.pokedex.network

import com.gustavo.pokedex.model.PokemonApiResult
import com.gustavo.pokedex.model.PokemonsApiResult
import com.gustavo.pokedex.network.interfaces.NetworkResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun listPokemons(@Query("limit") limit: Int): NetworkResult<PokemonsApiResult>

    @GET("pokemon/{number}")
    suspend fun getPokemon(@Path("number") number: Int): NetworkResult<PokemonApiResult>
}