package com.dh.gymhelper.di

import com.dh.gymhelper.BuildConfig
import com.dh.gymhelper.data.network.Api
import com.dh.gymhelper.di.AppModule.Companion.loggingInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "http://192.168.88.244:8080"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private val loggingInterceptor =
            HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    // provide httpClient to app
    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // method that provides gson
    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    // method that create retrofit
    @Singleton
    @Provides
    fun provideRetrofit(
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(providesGson()))
            .build()
    }


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}