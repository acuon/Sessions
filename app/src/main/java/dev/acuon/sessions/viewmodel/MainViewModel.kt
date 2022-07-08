package dev.acuon.sessions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.acuon.sessions.repository.MainRepository
import dev.acuon.sessions.ui.model.Dog
import dev.acuon.sessions.ui.model.image.BreedImageAll
import dev.acuon.sessions.ui.model.image.BreedImageSingle
import dev.acuon.sessions.utils.Extensions.randomIndex
import kotlinx.coroutines.*

class MainViewModel(private val repository: MainRepository, private val breed: String) :
    ViewModel() {
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
        CoroutineScope(Dispatchers.Main).launch {
            while (NonCancellable.isActive) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.getFiveRandomBreed()
                }
                delay(3000)
            }
        }
    }

    val breeds: LiveData<List<String>>
        get() = repository.allBreeds

    val breedImage: LiveData<BreedImageSingle>
        get() = repository.breedImage

    val breedImages: LiveData<BreedImageAll>
        get() = repository.breedImages

    val fiveRandomBreeds: LiveData<List<Dog>>
        get() = repository.fiveRandomBreeds
}