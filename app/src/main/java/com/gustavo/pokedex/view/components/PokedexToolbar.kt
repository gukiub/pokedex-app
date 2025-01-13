package com.gustavo.pokedex.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gustavo.pokedex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexToolbar() {
    TopAppBar(
        title = {
            Text(
                text = "POKEDEX",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_pokebola),
                contentDescription = "Pokebola Icon",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp),
                tint = Color.Unspecified
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF8B0000))
    )
}