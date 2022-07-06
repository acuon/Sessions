package dev.acuon.sessions.ui.postdetails.model

data class CommentItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)