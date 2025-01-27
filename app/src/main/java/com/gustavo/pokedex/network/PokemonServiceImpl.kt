package com.gustavo.pokedex.network

import com.gustavo.pokedex.model.PokemonApiResult
import com.gustavo.pokedex.model.PokemonsApiResult

interface PokemonServiceImpl {
    fun listPokemons(
        limit: Int = 151,
        onResult: (PokemonsApiResult?) -> Unit,
        onError: (Throwable) -> Unit,
        isLoading: (Boolean) -> Unit
    )

    fun getPokemon(
        number: Int,
        onResult: (PokemonApiResult?) -> Unit,
        onError: (Throwable) -> Unit,
        isLoading: (Boolean) -> Unit
    )
}