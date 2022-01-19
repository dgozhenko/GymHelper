package com.dh.gymhelper.data.datasource

import android.util.Log
import com.dh.gymhelper.data.network.Api
import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.UseCaseResult
import com.dh.gymhelper.presentation.extensions.mapSuccess
import com.dh.gymhelper.presentation.extensions.repoCall
import com.dh.gymhelper.presentation.extensions.safeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserDataSourceImpl(private val api: Api): UserDataSource {
    override suspend fun createUser(user: User): String = repoCall {
        api.createUser(user)
    }

    override suspend fun login(credentials: Credentials): String = repoCall{
        api.login(credentials = credentials)
    }
}