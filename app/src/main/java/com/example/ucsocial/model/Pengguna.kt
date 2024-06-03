package com.example.ucsocial.model

import com.google.gson.annotations.SerializedName

data class Pengguna(
    @SerializedName("bio") val bio: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("email") val email: String,
    @SerializedName("email_verified_at") val emailVerifiedAt: Any,
    @SerializedName("friend_id") val friendId: Any,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("nim") val nim: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("prodi_id") val prodiId: Int,
    @SerializedName("updated_at") val updatedAt: String
)