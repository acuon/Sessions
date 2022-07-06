package dev.acuon.sessions.ui.post.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
): Serializable