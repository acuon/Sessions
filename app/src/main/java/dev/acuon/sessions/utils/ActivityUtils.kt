package dev.acuon.sessions.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.ImageActivity
import dev.acuon.sessions.ui.MainActivity

object ActivityUtils {
    private lateinit var activity: AppCompatActivity
    fun intent(itemId: Int, context: Context) {
        when (itemId) {
            R.id.postImageActivity -> {
                activity = ImageActivity()
            }
            R.id.postActivity -> {
                activity = MainActivity()
            }
        }
        val intent = Intent(context, activity::class.java)
        if (activity.javaClass.name != this.javaClass.name) {
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
}