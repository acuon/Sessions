package dev.acuon.sessions.utils

import android.R.array
import dev.acuon.sessions.model.OsVersion
import java.util.*
import kotlin.collections.ArrayList


object Utils {

    const val DELETE = "Delete"
    const val DELETE_MESSAGE = "Are you sure you want to delete this item?"
    const val YES = "Yes"
    const val NO = "No"
    const val NAME_ERROR = "Name can't be empty"
    const val RELEASE_DATE_ERROR = "Date can't be empty"
    const val DESCRIPTION_ERROR = "Description can't be empty"
    const val DATE_FORMAT = "dd-MM-yyyy"
    const val YEAR_FORMAT = "yyyy"
    const val SPINNER_VERSION_TAG = "Select Android Version"
    const val SPINNER_SDK_TAG = "Select SDK Version"
    const val ADD = "Add"
    const val UPDATE = "Update"

    fun dummyData(): ArrayList<OsVersion> {
        var osList = ArrayList<OsVersion>()
        for (i in 1..5) {
            osList.add(
                OsVersion(
                    "Oreo",
                    8,
                    arrayOf(21, 8, 2017),
                    28,
                    "Android Oreo 8.0 is the eighth major update to the Android operating system that contains newer features and enhancements for application developers.",
                )
            )
        }
        return osList
    }
    fun osSdk(): ArrayList<Int> {
        val sdks = ArrayList<Int>()
        for(i in 1..33) sdks.add(i)
        return sdks
    }
    fun osVersions(): ArrayList<String> {
        val versions = ArrayList<String>()
        for(i in 1..13) {
            versions.add("Android $i")
        }
        return versions
    }
}