package com.example.ucsocial.model

import com.google.gson.annotations.SerializedName

data class CreateContent(
    @SerializedName("headline") val headline: String,
    @SerializedName("content_text") val content_text: String,
    @SerializedName("category_id") val category_id: String,
)