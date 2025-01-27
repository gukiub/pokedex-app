package com.gustavo.pokedex.view

import android.util.Log
import androidx.lifecycle.ViewModel
import com.gustavo.pokedex.model.Pokemon
import com.gustavo.pokedex.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

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

    private var offset = 0
    private val limit = 20
    private var hasMore = true

    init {
        Log.d("PokedexViewModel", "Carregando Pokémons iniciais...")
        loadPokemons()
    }

    fun loadPokemons(limit: Int = 20) {
        if (_isLoading.value || !hasMore) {
            Log.d(
                "PokedexViewModel",
                "Não carregar mais: isLoading=${_isLoading.value}, hasMore=$hasMore"
            )
            return
        }

        _isLoading.value = true
        Log.d("PokedexViewModel", "Iniciando carregamento: offset=$offset, limit=$limit")

        pokemonUseCase.listPokemons(
            limit = limit,
            offset = offset,
            onResult = { pokemonsApiResult ->
                pokemonsApiResult?.let { apiResult ->
                    val pokemonMap = _pokemons.value.associateBy { it.id }.toMutableMap()

                    apiResult.results.forEach { pokemonResult ->
                        val number = pokemonResult.url
                            .replace("https://pokeapi.co/api/v2/pokemon/", "")
                            .replace("/", "").toInt()

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
                                    _pokemons.value = pokemonMap.toSortedMap().values.toList()
                                }
                            },
                            onError = { throwable ->
                                _error.value = "Erro ao carregar Pokémon: ${throwable.message}"
                            },
                            isLoading = {}
                        )
                    }

                    offset += limit
                    hasMore = apiResult.results.size == limit

                    Log.d(
                        "PokedexViewModel",
                        "Carregamento concluído: offset=$offset, hasMore=$hasMore"
                    )
                } ?: run {
                    hasMore = false
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