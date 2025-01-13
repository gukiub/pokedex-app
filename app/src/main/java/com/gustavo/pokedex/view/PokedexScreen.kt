package com.gustavo.pokedex.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.gustavo.pokedex.view.components.LoadingAnimation
import com.gustavo.pokedex.view.theme.PokedexTheme

object PokedexScreen : Screen {
    private fun readResolve(): Any = PokedexScreen

    @Composable
    override fun Content() {
        PokedexTheme {
            PokedexScreenContent()
        }
    }
}

@Composable
fun PokedexScreenContent(viewModel: PokedexViewModel = hiltViewModel()) {
    val pokemons by viewModel.pokemons.collectAsState()

    if (pokemons.isEmpty()) {
        LoadingAnimation()
    } else {
        PokemonList(pokemons = pokemons)
    }
}