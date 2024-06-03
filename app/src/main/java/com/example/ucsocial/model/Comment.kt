package com.example.ucsocial.model
import com.google.gson.annotations.SerializedName

data class Commentku (
    @SerializedName("content_id") val content_id: String,
    @SerializedName("comment_text") val comment_text: String,
    @SerializedName("user") val user: User
)

data class CommentReq (
    @SerializedName("content_id") val content_id: String,
    @SerializedName("comment_text") val comment_text: String,
    )