package com.gustavo.pokedex.network

import com.gustavo.pokedex.model.PokemonApiResult
import com.gustavo.pokedex.model.PokemonsApiResult
import com.gustavo.pokedex.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val service: PokemonService
) {
    fun listPokemons(limit: Int = 151): PokemonsApiResult? {
        val call = service.listPokemons(limit)
        return call.execute().body()
    }

    fun getPokemon(number: Int): PokemonApiResult? {
        val call = service.getPokemon(number)
        return call.execute().body()
    }
}