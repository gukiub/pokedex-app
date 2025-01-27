package com.gustavo.pokedex.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.pokedex.model.Pokemon
import com.gustavo.pokedex.network.PokemonRepository
import com.gustavo.pokedex.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase
) : ViewModel() {

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadPokemons()
    }

    private fun loadPokemons(limit: Int = 20) {
        _isLoading.value = true
        pokemonUseCase.listPokemons(
            limit = limit,
            onResult = { pokemonsApiResult ->
                pokemonsApiResult?.let {
                    val loadedPokemons = it.results.mapNotNull { pokemonResult ->
                        val number = pokemonResult.url
                            .replace("https://pokeapi.co/api/v2/pokemon/", "")
                            .replace("/", "").toInt()

                        var pokemon: Pokemon? = null
                        pokemonUseCase.getPokemon(
                            number = number,
                            onResult = { pokemonApiResult ->
                                pokemon = pokemonApiResult?.let { result ->
                                    Pokemon(
                                        id = result.id,
                                        name = result.name,
                                        types = result.types.map { it.type }
                                    )
                                }
                            },
                            onError = { throwable ->
                                _error.value = throwable.message
                            },
                            isLoading = {}
                        )
                        pokemon
                    }
                    _pokemons.value = loadedPokemons
                } ?: run {
                    _pokemons.value = emptyList()
                }
                _isLoading.value = false
            },
            onError = { throwable ->
                _error.value = throwable.message
                _isLoading.value = false
            },
            isLoading = { isLoading ->
                _isLoading.value = isLoading
            }
        )
    }
}