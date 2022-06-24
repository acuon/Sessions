package dev.acuon.sessions.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.activity.TabLayoutWithViewPager
import dev.acuon.sessions.ui.activity.TabLayoutWithoutViewPager
import dev.acuon.sessions.ui.activity.ViewPager2SliderActivity

object ActivityUtils {
    private lateinit var activity: AppCompatActivity
    fun intent(itemId: Int, context: Context) {
        when (itemId) {
            R.id.tabLayoutWithoutViewpager -> {
                activity = TabLayoutWithoutViewPager()
            }
            R.id.viewpager2SliderActivity -> {
                activity = ViewPager2SliderActivity()
            }
            R.id.tabLayoutWithViewpager -> {
                activity = TabLayoutWithViewPager()
            }
        }
        val intent = Intent(context, activity::class.java)
        if (activity.javaClass.name != this.javaClass.name) {
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
}