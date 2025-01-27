package com.gustavo.pokedex.di

import com.google.gson.Gson
import com.gustavo.pokedex.network.PokemonService
import com.gustavo.pokedex.network.factory.createGsonInstance
import com.gustavo.pokedex.network.factory.createOkHttpClient
import com.gustavo.pokedex.network.retrofit.createRetrofitService
import com.gustavo.pokedex.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGsonInstance(): Gson {
        return createGsonInstance()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return createOkHttpClient()
    }

    @Provides
    @Singleton
    fun providePokemonService(): PokemonService {
        return createRetrofitService(
            createGsonInstance(),
            createOkHttpClient(),
            PokemonService::class.java,
            BASE_URL
        )
    }
}