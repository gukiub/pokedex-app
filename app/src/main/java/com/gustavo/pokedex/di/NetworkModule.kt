package com.gustavo.pokedex.di

import com.google.gson.Gson
import com.gustavo.pokedex.network.PokemonService
import com.gustavo.pokedex.network.factory.createGsonInstance
import com.gustavo.pokedex.network.factory.createOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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
    fun providePokemonService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}