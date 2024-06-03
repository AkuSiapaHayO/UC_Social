package com.example.ucsocial.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class UserUpdateReq2(

    @SerializedName("name") val name: String,
    @SerializedName("bio") val bio: String
)
