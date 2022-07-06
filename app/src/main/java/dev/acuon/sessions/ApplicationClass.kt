package dev.acuon.sessions

import android.app.Application
import dev.acuon.sessions.data.api.ApiService
import dev.acuon.sessions.data.api.RetrofitHelper
import dev.acuon.sessions.repository.MainRepository

class ApplicationClass: Application() {
    lateinit var repository: MainRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val recipesService = RetrofitHelper.getInstance().create(ApiService::class.java)
        repository = MainRepository(recipesService)
    }
}