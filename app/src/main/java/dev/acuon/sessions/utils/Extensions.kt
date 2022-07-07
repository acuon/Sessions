package dev.acuon.sessions.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import dev.acuon.sessions.utils.Extensions.random
import kotlin.random.Random

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
    fun ArrayList<String>.randomIndex(): Int {
        return Int.random(this.size)
    }
    private fun Int.Companion.random(size: Int): Int {
        return Random.nextInt(size)
    }
}