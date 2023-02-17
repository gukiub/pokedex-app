package com.gustavo.pokedex.model

import java.util.*

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>
){
    fun formattedId(): String{
        val formattedNumber = id.toString().padStart(3, '0')
        val stringBuilder = StringBuilder()
        stringBuilder.append("N° ")
        stringBuilder.append(formattedNumber)
        return stringBuilder.toString()
    }

    val formattedName = name.uppercase(Locale.ROOT)

    private val formattedNumber = id.toString().padStart(3, '0')

    val imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$formattedNumber.png"
}

