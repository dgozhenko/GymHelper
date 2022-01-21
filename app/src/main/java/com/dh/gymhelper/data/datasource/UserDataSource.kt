package com.dh.gymhelper.data.datasource

import com.dh.gymhelper.domain.user.Credentials
import com.dh.gymhelper.domain.user.ProfileImage
import com.dh.gymhelper.domain.user.User

interface UserDataSource {

    suspend fun createUser(user: User): String

    suspend fun login(credentials: Credentials): String

    suspend fun getUserByEmail(email: Map<String, String>): User

    suspend fun getUser(): User

    suspend fun updateProfileImage(profileImage: ProfileImage): Boolean

    suspend fun getProfileImage(): ProfileImage
}