package com.dh.gymhelper.data.network

import android.content.Context
import com.dh.gymhelper.presentation.util.SessionManager

class AuthInterceptor(context: Context) : okhttp3.Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder
                .addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}