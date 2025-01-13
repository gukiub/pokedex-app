package com.gustavo.pokedex.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.pokedex.model.Pokemon
import com.gustavo.pokedex.network.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            val loadedPokemons = withContext(Dispatchers.IO) {
                val pokemonsApiResult = repository.listPokemons(20)
                pokemonsApiResult?.results?.mapNotNull { pokemonResult ->
                    val number = pokemonResult.url
                        .replace("https://pokeapi.co/api/v2/pokemon/", "")
                        .replace("/", "").toInt()

                    repository.getPokemon(number)?.let { pokemonApiResult ->
                        Pokemon(
                            id = pokemonApiResult.id,
                            name = pokemonApiResult.name,
                            types = pokemonApiResult.types.map { it.type }
                        )
                    }
                }
            } ?: emptyList()

            _pokemons.value = loadedPokemons
        }
    }
}