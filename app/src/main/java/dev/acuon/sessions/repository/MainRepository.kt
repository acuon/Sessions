package dev.acuon.sessions.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.acuon.sessions.data.api.ApiService
import dev.acuon.sessions.ui.postdetails.model.CommentItem
import dev.acuon.sessions.ui.post.model.user.User
import dev.acuon.sessions.ui.post.model.PostItem
import dev.acuon.sessions.ui.postImage.model.PostImageItem

class MainRepository(
    private val apiService: ApiService
) {
    private val postsLiveData = MutableLiveData<List<PostItem>>()
    private val postCommentsLiveData = MutableLiveData<List<CommentItem>>()
    private val postUsers = MutableLiveData<List<User>>()
    private val postImages = MutableLiveData<List<PostImageItem>>()

    val allPosts: LiveData<List<PostItem>>
        get() = postsLiveData

    val allComments: LiveData<List<CommentItem>>
        get() = postCommentsLiveData

    val allUsers: LiveData<List<User>>
        get() = postUsers

    val allImages: LiveData<List<PostImageItem>>
        get() = postImages

    suspend fun getAllPosts() {
        val result = apiService.getAllPosts()
        if (result.body() != null) {
            postsLiveData.postValue(result.body())
        }
    }

    suspend fun getAllComments(postId: Int) {
        val result = apiService.getPostComments(postId)
        if (result.body() != null) {
            postCommentsLiveData.postValue(result.body())
        }
    }

    suspend fun getAllUsers() {
        val result = apiService.getAllUsers()
        if (result.body() != null) {
            postUsers.postValue(result.body())
        }
    }

    suspend fun getAllImages() {
        val result = apiService.getPostImages()
        if (result.body() != null) {
            postImages.postValue(result.body())
        }
    }
}