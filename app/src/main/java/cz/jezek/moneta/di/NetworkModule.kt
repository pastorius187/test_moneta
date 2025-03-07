package cz.jezek.moneta.di

import cz.jezek.moneta.core.Constants
import cz.jezek.moneta.data.network.HttpClient
import cz.jezek.moneta.data.network.NBAApi
import cz.jezek.moneta.data.repository.BalldontlieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * DI Module Network poskytuje objekty pro komunikaci s API
 *
 * @see Retrofit
 * @see NBAApi
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = HttpClient.retrofit

    @Provides
    @Singleton
    fun provideNBAApi(retrofit: Retrofit): NBAApi = retrofit.create(NBAApi::class.java)

    @Provides
    @Singleton
    fun provideBalldontlieRepository(api: NBAApi) = BalldontlieRepository(api)

}