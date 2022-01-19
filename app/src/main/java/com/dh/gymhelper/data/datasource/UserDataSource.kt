package com.dh.gymhelper.data.datasource

import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.UseCaseResult
import retrofit.http.Body
import java.util.concurrent.Flow

interface UserDataSource {

     suspend fun createUser(user: User): String

     suspend fun login(credentials: Credentials): String
}