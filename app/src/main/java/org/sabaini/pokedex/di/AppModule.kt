package org.sabaini.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.sabaini.pokedex.data.PokemonApi
import org.sabaini.pokedex.data.PokemonRemoteDataSource
import org.sabaini.pokedex.data.PokemonRepository
import org.sabaini.pokedex.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun providePokedexRepository(pokemonRemoteDataSource: PokemonRemoteDataSource): PokemonRepository {
        return PokemonRepository(pokemonRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providePokedexRemoteDataSource(
        pokemonApi: PokemonApi,
        ioDispatcher: CoroutineDispatcher
    ): PokemonRemoteDataSource {
        return PokemonRemoteDataSource(pokemonApi, ioDispatcher)
    }

    @Singleton
    @Provides
    fun providePokedexApi(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }
}