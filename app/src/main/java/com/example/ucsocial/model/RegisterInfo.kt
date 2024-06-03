package com.example.ucsocial.model

import com.google.gson.annotations.SerializedName

data class RegisterInfo(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("nim") val nim: String,
    @SerializedName("password") val password: String,
    @SerializedName("prodi_id") val prodiId: Int
)