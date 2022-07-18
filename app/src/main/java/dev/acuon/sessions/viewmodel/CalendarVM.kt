package dev.acuon.sessions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.acuon.sessions.model.Reminder
import dev.acuon.sessions.repo.CalendarRepository

class CalendarVM(private val repo: CalendarRepository) : ViewModel() {
    fun insertDataInReminder(reminder: Reminder) {
        repo.addDataToReminder(reminder)
    }

    fun getReminderByDate(date: String): LiveData<List<Reminder>> {
        return repo.getReminderByDate(date)
    }

    fun deleteFromReminder(reminder: Reminder) {
        repo.deleteFomReminder(reminder)
    }
}