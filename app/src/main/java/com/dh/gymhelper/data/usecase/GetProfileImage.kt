package com.dh.gymhelper.data.usecase

import com.dh.gymhelper.data.datasource.UserDataSource
import com.dh.gymhelper.domain.user.ProfileImage
import com.dh.gymhelper.presentation.extensions.UseCaseResult
import com.dh.gymhelper.presentation.extensions.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileImage @Inject constructor(private val userDataSource: UserDataSource) {

    fun getProfileImage(): Flow<UseCaseResult<ProfileImage>> = safeFlow {
        userDataSource.getProfileImage()
    }

}