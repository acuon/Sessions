package dev.acuon.sessions.utils

import android.content.Context
import android.view.View
import android.widget.Toast

object Extensions {
    fun Context.showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
    fun View.hide() {
        this.visibility = View.INVISIBLE
    }
    fun View.show() {
        this.visibility = View.VISIBLE
    }
    fun View.gone() {
        this.visibility = View.GONE
    }
}