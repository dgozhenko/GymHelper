package com.dh.gymhelper.data.network

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie

import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*
import kotlin.collections.ArrayList


class SessionCookieJar(context: Context) {
    private val cookieJar: ClearableCookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

    fun createCookieJar(): ClearableCookieJar {
        return cookieJar
    }

    fun clearCookieJar() {
        cookieJar.clear()
    }
}