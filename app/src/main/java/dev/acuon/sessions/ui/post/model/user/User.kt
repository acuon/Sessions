package dev.acuon.sessions.ui.post.model.user

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    /*val address: Address,
    val company: Company,*/
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: String
): Serializable