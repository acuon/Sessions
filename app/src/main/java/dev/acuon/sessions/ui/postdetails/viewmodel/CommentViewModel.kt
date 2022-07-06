package dev.acuon.sessions.ui.postdetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.acuon.sessions.repository.MainRepository
import dev.acuon.sessions.ui.postdetails.model.CommentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: MainRepository, private val postId: Int): ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllComments(postId)
        }
    }

    val comments: LiveData<List<CommentItem>>
        get() = repository.allComments
}