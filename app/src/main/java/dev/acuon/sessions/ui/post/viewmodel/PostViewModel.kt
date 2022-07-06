package dev.acuon.sessions.ui.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.acuon.sessions.repository.MainRepository
import dev.acuon.sessions.ui.post.model.user.User
import dev.acuon.sessions.ui.post.model.PostItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(private val repository: MainRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPosts()
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllUsers()
        }
    }

    val posts: LiveData<List<PostItem>>
        get() = repository.allPosts

    val users: LiveData<List<User>>
        get() = repository.allUsers
}