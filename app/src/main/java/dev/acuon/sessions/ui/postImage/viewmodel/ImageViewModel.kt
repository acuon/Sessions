package dev.acuon.sessions.ui.postImage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.acuon.sessions.repository.MainRepository
import dev.acuon.sessions.ui.postImage.model.PostImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: MainRepository): ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllImages()
        }
    }

    val images: LiveData<List<PostImageItem>>
        get() = repository.allImages
}