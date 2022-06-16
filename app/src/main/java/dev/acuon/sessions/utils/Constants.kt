package dev.acuon.sessions.utils

import android.graphics.Color
import kotlin.random.Random

object Constants {
    const val SIMPLE_DIALOG = "Simple Dialog"
    const val DIALOG_WITH_BUTTONS = "Dialog With Buttons"
    const val SINGLE_CHOICE_DIALOG = "Single Choice Dialog"
    const val MULTI_CHOICE_DIALOG = "Multi Choice Dialog"
    const val CIRCULAR_PROGRESS_DIALOG = "Circular Progress Dialog"
    const val DATE_PICKER_DIALOG = "Date Picker Dialog"
    const val TIME_PICKER_DIALOG = "Time Picker Dialog"
    const val BOTTOM_DIALOG = "Bottom Dialog"
    const val CUSTOM_DIALOG = "Custom Dialog"

    private fun Int.Companion.randomColor(): Int {
        return Color.argb(
            255,
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }
    fun getRandomColor(): Int {
        return Int.randomColor()
    }


    val OPTIONS_ARRAY = arrayOf("Option 1", "Option 2", "Option 3")
    val COLORS_ARRAY = arrayOf("Black", "Orange", "Green", "Yellow", "White", "Purple")
    val CHECKED_COLORS_ARRAY = booleanArrayOf(false, false, false, false, false, false)
}