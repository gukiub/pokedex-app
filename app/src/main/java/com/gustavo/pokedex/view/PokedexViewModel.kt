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
                pokemonsApiResult?.let { apiResult ->
                    val pokemonMap = mutableMapOf<Int, Pokemon?>()

                    apiResult.results.forEach { pokemonResult ->
                        val number = pokemonResult.url
                            .replace("https://pokeapi.co/api/v2/pokemon/", "")
                            .replace("/", "").toInt()

                        pokemonMap[number] = null

                        pokemonUseCase.getPokemon(
                            number = number,
                            onResult = { pokemonApiResult ->
                                pokemonApiResult?.let { result ->
                                    val pokemon = Pokemon(
                                        id = result.id,
                                        name = result.name,
                                        types = result.types.map { it.type }
                                    )

                                    pokemonMap[number] = pokemon

                                    _pokemons.value = pokemonMap.toSortedMap().values.filterNotNull()
                                }
                            },
                            onError = { throwable ->
                                _error.value = "Erro ao carregar Pokémon: ${throwable.message}"
                            },
                            isLoading = {}
                        )
                    }
                } ?: run {
                    _pokemons.value = emptyList()
                    _error.value = "Nenhum Pokémon encontrado."
                }

                _isLoading.value = false
            },
            onError = { throwable ->
                _error.value = "Erro ao carregar Pokémons: ${throwable.message}"
                _isLoading.value = false
            },
            isLoading = { isLoading ->
                _isLoading.value = isLoading
            }
        )
    }
}