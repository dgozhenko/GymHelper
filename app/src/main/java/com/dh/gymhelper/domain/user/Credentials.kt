package com.dh.gymhelper.domain.user

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
