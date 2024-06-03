package com.example.ucsocial.model

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("id") val id: Int,
    @SerializedName("headline") val headline: String,
    @SerializedName("image") val image: String,
    @SerializedName("content_text") val contentText: String,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("user") val user: User
)