package dev.acuon.sessions.utils

import dev.acuon.sessions.model.OsVersion

object Constants {
    const val WIDGETS = "Widgets"
    const val LISTVIEW = "ListView"
    const val RECYCLER_VIEW = "RecyclerView"
    const val CUSTOM_LISTVIEW = "Custom ListView"

    fun dummyData(): ArrayList<OsVersion> {
        val osList = ArrayList<OsVersion>()
        for (i in 1..10) {
            osList.add(
                OsVersion(
                    "Oreo",
                    "Version 8",
                    2018,
                    28,
                    "Android Oreo 8.0 is the eighth major update to the Android operating system that contains newer features and enhancements for application developers."
                )
            )
        }
        return osList
    }
    val osNames = arrayListOf(
        "Angel Cake",
        "Battenberg",
        "Cupcake",
        "Donut",
        "Eclair",
        "Froyo",
        "GingerBread",
        "HoneyComb",
        "Ice Cream Sandwich",
        "Jelly Bean",
        "Kitkat",
        "Lollipop",
        "Marshmallow",
        "Nougat",
        "Oreo"
    )
}