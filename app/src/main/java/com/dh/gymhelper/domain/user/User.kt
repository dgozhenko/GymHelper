package com.dh.gymhelper.domain.user

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("passwordHash")
    val passwordHash: String? = null,
    @SerializedName("health")
    val health: Health? = null,
    @SerializedName("trainingIds")
    val trainingIds: List<String>? = null,
    @SerializedName("exerciseIds")
    val exerciseIds: List<String>? = null,
    @SerializedName("personalBestIds")
    val personalBestIds: List<String>? = null
)
