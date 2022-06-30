package dev.acuon.sessions.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.ImageActivity
import dev.acuon.sessions.ui.MapActivity

object ActivityUtils {
    private lateinit var activity: AppCompatActivity
    fun intent(itemId: Int, context: Context) {
        when (itemId) {
            R.id.action_map_activity -> {
                activity = MapActivity()
            }
            R.id.action_image_activity -> {
                activity = ImageActivity()
            }
        }
        val intent = Intent(context, activity::class.java)
        if (activity.javaClass.name != this.javaClass.name) {
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
}