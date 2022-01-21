package com.dh.gymhelper.domain.user

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImage(
    @SerializedName("profileImage")
    val profileImage: String
)
