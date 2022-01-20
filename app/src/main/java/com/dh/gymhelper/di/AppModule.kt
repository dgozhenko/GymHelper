package com.dh.gymhelper.di

import android.content.Context
import android.content.SharedPreferences
import com.dh.gymhelper.R
import com.dh.gymhelper.data.network.Api
import com.dh.gymhelper.data.network.AuthInterceptor
import com.dh.gymhelper.data.network.SessionCookieJar
import com.dh.gymhelper.presentation.util.SessionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Singleton

const val BASE_URL = "http://192.168.88.244:8080"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private val loggingInterceptor =
            HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesSessionManager(@ApplicationContext context: Context) = SessionManager(context)

    // provide httpClient to app
    @Singleton
    @Provides
    fun providesHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return OkHttpClient()
            .newBuilder()
            .cookieJar(SessionCookieJar())
            .retryOnConnectionFailure(true)
            .addInterceptor(AuthInterceptor(context))
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
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(providesGson()))
            .build()
    }


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}