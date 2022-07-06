package dev.acuon.sessions.data.api

import dev.acuon.sessions.ui.postdetails.model.CommentItem
import dev.acuon.sessions.ui.post.model.user.User
import dev.acuon.sessions.ui.postImage.model.PostImageItem
import dev.acuon.sessions.ui.post.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getAllPosts(): Response<Post>

    @GET("photos")
    suspend fun getPostImages(): Response<List<PostImageItem>>

    @GET("users")
    suspend fun getAllUsers(/*@Path("userId") userId: Int*/): Response<List<User>>

    @GET("comments")
    suspend fun getPostComments(@Query("postId") postId: Int): Response<List<CommentItem>>
}