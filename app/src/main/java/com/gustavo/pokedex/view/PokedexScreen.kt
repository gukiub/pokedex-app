package com.gustavo.pokedex.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.gustavo.pokedex.R
import com.gustavo.pokedex.view.components.LoadingAnimation
import com.gustavo.pokedex.view.components.PokedexToolbar
import com.gustavo.pokedex.view.theme.PokedexTheme

object PokedexScreen : Screen {
    private fun readResolve(): Any = PokedexScreen

    @Composable
    override fun Content() {
        PokedexTheme {
            Scaffold(
                topBar = { PokedexToolbar() },
                contentWindowInsets = WindowInsets(0)
            ) { innerPadding ->
                PokedexScreenContent(
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun PokedexScreenContent(
    modifier: Modifier = Modifier,
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val pokemons by viewModel.pokemons.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokedex_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (pokemons.isEmpty()) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            PokemonList(modifier = Modifier.fillMaxSize(), pokemons = pokemons)
        }
    }
}