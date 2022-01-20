package com.dh.gymhelper.data.datasource

import com.dh.gymhelper.data.network.Api
import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.repoCall

class UserDataSourceImpl(private val api: Api) : UserDataSource {

    override suspend fun createUser(user: User): String = repoCall {
        api.createUser(user = user)
    }

    override suspend fun login(credentials: Credentials): String = repoCall {
        api.login(credentials = credentials)
    }

    override suspend fun getUserByEmail(email: Map<String, String>): User = repoCall {
        api.getUserByEmail(email = email)
    }

    override suspend fun getUser(): User = repoCall {
        api.getUser()
    }
}