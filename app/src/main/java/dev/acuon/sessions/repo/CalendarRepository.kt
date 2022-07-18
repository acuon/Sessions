package dev.acuon.sessions.repo

import androidx.lifecycle.LiveData
import dev.acuon.sessions.data.CalendarDao
import dev.acuon.sessions.model.Reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarRepository(
    private val dao: CalendarDao
) {
    fun addDataToReminder(reminder: Reminder) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertDataInReminder(reminder)
        }
    }

    fun getReminderByDate(date: String): LiveData<List<Reminder>> {
        return dao.getReminderByDate(date)
    }

    fun deleteFomReminder(reminder: Reminder) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteFromReminder(reminder)
        }
    }
}