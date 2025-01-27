package com.gustavo.pokedex.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gustavo.pokedex.model.Pokemon
import com.gustavo.pokedex.view.components.LoadingAnimation

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    pokemons: List<Pokemon>,
    onLoadMore: () -> Unit,
    isLoading: Boolean
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemons) { pokemon ->
            PokedexItem(pokemon)
        }

        if (isLoading) {
            item {
                LoadingAnimation(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleIndex ->
            println("Último índice visível: $lastVisibleIndex / Total de Pokémons: ${pokemons.size}")

            if (lastVisibleIndex != null && lastVisibleIndex >= pokemons.size - 1 && !isLoading) {
                onLoadMore()
            }
        }
    }
}