package com.example.ucsocial.model

import kotlinx.serialization.SerialName

data class UserUpdateRequest (
    @SerialName("name") val name: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("bio") val bio: String? = null
)