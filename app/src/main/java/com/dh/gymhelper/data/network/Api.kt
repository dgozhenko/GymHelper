package com.dh.gymhelper.data.network

import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("/v1/users/create")
    suspend fun createUser(@Body user: User): Response<String>


    @POST("/v1/users/login")
    suspend fun login(@Body credentials: Credentials): Response<String>

    @GET("/v1/users/email/")
    suspend fun getUserByEmail(@Body email: Map<String, String>): Response<User>

    @GET("/v1/users/")
    suspend fun getUser(): Response<User>

}
