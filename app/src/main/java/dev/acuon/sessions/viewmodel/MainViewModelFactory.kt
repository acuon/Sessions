package dev.acuon.sessions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.acuon.sessions.repository.MainRepository

class MainViewModelFactory(private val repository: MainRepository, private val breed: String = "affenpinscher"): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, breed) as T
    }
}