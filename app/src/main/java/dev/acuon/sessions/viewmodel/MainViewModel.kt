package dev.acuon.sessions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.acuon.sessions.repository.MainRepository
import dev.acuon.sessions.ui.model.image.BreedImageAll
import dev.acuon.sessions.ui.model.image.BreedImageSingle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository, private val breed: String) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBreeds()
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSingleImage(breed)
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllImages(breed)
        }
    }

    val breeds: LiveData<List<String>>
        get() = repository.allBreeds

    val breedImage: LiveData<BreedImageSingle>
        get() = repository.breedImage

    val breedImages: LiveData<BreedImageAll>
        get() = repository.breedImages
}