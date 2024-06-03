package com.example.ucsocial.model

import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("pengguna") val pengguna: Pengguna,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)