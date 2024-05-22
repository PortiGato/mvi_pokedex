package com.example.mvi_pokedex.di

import com.example.mvi_pokedex.data.network.api.PokeApi
import com.example.mvi_pokedex.utils.Constants.BASE_URL
import com.example.mvi_pokedex.utils.Constants.TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    @Named("InterceptorApp")
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Named("HttpClientApp")
    fun provideOkHttpClientApp(@Named("InterceptorApp") interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Named("RetrofitApp")
    fun provideRetrofitApp(@Named("HttpClientApp") okHttpClient: OkHttpClient, @Named("baseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePokeApi(@Named("RetrofitApp") retrofit: Retrofit): PokeApi =
        retrofit.create(PokeApi::class.java)

}
