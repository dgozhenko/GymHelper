package com.dh.gymhelper.data.network

import okhttp3.Cookie

import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*
import kotlin.collections.ArrayList


class SessionCookieJar : CookieJar {
    private var cookies: List<Cookie>? = null

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return if (!url.encodedPath.endsWith("login") && cookies != null) {
            cookies!!
        } else Collections.emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.encodedPath.endsWith("login")) {
            this.cookies = ArrayList(cookies)
        }
    }
}