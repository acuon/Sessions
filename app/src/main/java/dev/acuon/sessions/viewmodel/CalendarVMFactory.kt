package dev.acuon.sessions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.acuon.sessions.repo.CalendarRepository

class CalendarVMFactory(private val repo:CalendarRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarVM(repo) as T
    }
}