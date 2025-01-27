package com.gustavo.pokedex.usecase

import com.gustavo.pokedex.model.PokemonApiResult
import com.gustavo.pokedex.model.PokemonsApiResult
import com.gustavo.pokedex.network.PokemonRepository
import javax.inject.Inject

class PokemonUseCase @Inject constructor(private val repository: PokemonRepository) {

    private var offset = 0
    private val limit = 20

    fun resetPagination() {
        offset = 0
    }

    fun listPokemons(
        limit: Int,
        offset: Int,
        onResult: (PokemonsApiResult?) -> Unit,
        onError: (Throwable) -> Unit,
        isLoading: (Boolean) -> Unit
    ) = repository.listPokemons(limit, offset, onResult, onError, isLoading)


    fun getPokemon(
        number: Int,
        onResult: (PokemonApiResult?) -> Unit,
        onError: (Throwable) -> Unit,
        isLoading: (Boolean) -> Unit
    ) = repository.getPokemon(number, onResult, onError, isLoading)
}