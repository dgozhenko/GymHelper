package com.dh.gymhelper.data.network

import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {

    @POST("/v1/users/create")
    suspend fun createUser(@Body user: User): Response<String>


    @POST("/v1/users/login")
    suspend fun login(@Body credentials: Credentials): Response<String>

}
