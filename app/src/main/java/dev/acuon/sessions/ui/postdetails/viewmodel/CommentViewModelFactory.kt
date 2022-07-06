package dev.acuon.sessions.ui.postdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.acuon.sessions.repository.MainRepository

class CommentViewModelFactory(private val repository: MainRepository, private val postId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentViewModel(repository, postId) as T
    }
}