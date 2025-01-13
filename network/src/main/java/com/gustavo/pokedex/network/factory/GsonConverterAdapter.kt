package com.gustavo.pokedex.network.factory

import com.google.gson.Gson
import com.google.gson.GsonBuilder

fun createGsonInstance(): Gson {
    return GsonBuilder().setLenient().create()
}