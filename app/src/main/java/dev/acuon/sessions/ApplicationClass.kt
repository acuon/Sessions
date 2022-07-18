package dev.acuon.sessions

import android.app.Application
import dev.acuon.sessions.data.CalendarDatabase
import dev.acuon.sessions.repo.CalendarRepository

class ApplicationClass : Application() {
    lateinit var repository: CalendarRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val database = CalendarDatabase.getDataBaseObject(applicationContext)
        repository = CalendarRepository(database.getDao())
    }
}