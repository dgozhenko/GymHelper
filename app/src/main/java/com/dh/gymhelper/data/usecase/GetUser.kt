package com.dh.gymhelper.data.usecase

import com.dh.gymhelper.data.datasource.UserDataSource
import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.User
import com.dh.gymhelper.presentation.extensions.UseCaseResult
import com.dh.gymhelper.presentation.extensions.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUser @Inject constructor(private val userDataSource: UserDataSource) {
    fun get(): Flow<UseCaseResult<User>> = safeFlow {
        userDataSource.getUser()
    }
}