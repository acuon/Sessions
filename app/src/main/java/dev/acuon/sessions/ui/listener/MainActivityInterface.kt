package dev.acuon.sessions.ui.listener

import androidx.fragment.app.Fragment

interface MainActivityInterface {
    fun openFragment(fragment: Fragment)
    fun progressBar(boolean: Boolean)
}