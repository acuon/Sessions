package dev.acuon.sessions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimpleViewModel : ViewModel() {
    private val message1to2 = MutableLiveData<String>()
    private var message2to1 = MutableLiveData<String>()

    val messageForFragmentTwo: LiveData<String>
        get() = message1to2

    val messageForFragmentOne: LiveData<String>
        get() = message2to1

    fun fragmentOneToTwo(text: String) {
        message1to2.value = text
    }

    fun fragmentTwoToOne(text: String) {
        message2to1.value = text
    }
}