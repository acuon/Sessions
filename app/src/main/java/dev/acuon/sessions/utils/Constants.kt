package dev.acuon.sessions.utils

import dev.acuon.sessions.model.OsVersion

object Constants {
    const val WIDGETS = "Widgets"
    const val LISTVIEW = "ListView"
    const val RECYCLER_VIEW = "RecyclerView"
    const val CUSTOM_LISTVIEW = "Custom ListView"

    fun dummyData(): ArrayList<OsVersion> {
        val osList = ArrayList<OsVersion>()
        val angelCake = OsVersion(
            "AngelCake",
            "Version 1.0",
            2008,
            1,
            "The first version of the open source software was released back in 2008. The OS along with its launch device, HTC Hero (also known as T-Mobile G1) was received very positively by the reviewers.",
            "A"
        )
        val battenBerg = OsVersion(
            "BattenBerg",
            "Version 1.1",
            2009,
            2,
            "In Feb 2009, version 1.1 was released. Although it took almost a year to materialise, it wasn't a major update.",
            "B"
        )
        val cupcake = OsVersion(
            "CupCake",
            "Version 1.5",
            2009,
            3,
            "Launched in April 2009, Cupcake offered video recording capabilities, widget support, and animated transition effects.",
            "C"
        )
        val donut = OsVersion(
            "Donut",
            "Version 1.6",
            2009,
            4,
            "Android 1.6 Donut is the fourth version of the open source Android mobile operating system developed by Google that is no longer supported.",
            "D"
        )
        val eclair = OsVersion(
            "Eclair",
            "Version 2.0",
            2009,
            7,
            "Android Eclair is a codename of the Android mobile operating system developed by Google, the fifth operating system for Android and the second major release of Android, and for the no-longer supported versions 2.0 to 2.1.",
            "E"
        )
        val froyo = OsVersion(
            "Froyo",
            "Version 2.2",
            2010,
            8,
            "Android 2.2 (otherwise known as Froyo, short for Frozen Yogurt) is a version of Android that was released on May 20 2010.",
            "F"
        )
        val gingerBread = OsVersion(
            "GingerBread",
            "Version 2.3",
            2010,
            10,
            "Android 2.3 Gingerbread is the seventh version of Android, a codename of the Android mobile operating system developed by Google and released in December 2010, for versions that are no longer supported.",
            "G"
        )
        val honeyComb = OsVersion(
            "HoneyComb",
            "Version 3.0",
            2011,
            13,
            "Android Honeycomb is the codename for the third version of Android, designed for devices with larger screen sizes, particularly tablets.",
            "H"
        )
        val iceCreamSandwich = OsVersion(
            "IceCream Sandwich",
            "Version 4.0",
            2011,
            15,
            "Android Ice Cream Sandwich (or Android 4.0) is the 9th major version of the Android mobile operating system developed by Google.",
            "I"
        )
        val jellyBean = OsVersion(
            "JellyBean",
            "Version 4.1",
            2012,
            18,
            "Android Jelly Bean is the codename given to the tenth version of the Android mobile operating system developed by Google.",
            "J"
        )
        val kitkat = OsVersion(
            "Kitkat",
            "Version 4.4",
            2013,
            19,
            "Android 4.4 KitKat is a version of Google's operating system (OS) for smartphones and tablets.",
            "K"
        )
        val lollipop = OsVersion(
            "Lollipop",
            "Version 5.0",
            2014,
            22,
            "Android Lollipop (codenamed Android L during development) is the fifth major version of the Android mobile operating system developed by Google and the 12th version of Android, spanning versions between 5.0 and 5.1. 1.",
            "L"
        )
        val marshmallow = OsVersion(
            "Marshmallow",
            "Version 6.0",
            2015,
            23,
            "Android Marshmallow provides native support for fingerprint recognition on supported devices via a standard API, allowing third-party applications to implement fingerprint-based authentication.",
            "M"
        )
        val nougat = OsVersion(
            "Nouagat",
            "Version 7.0",
            2016,
            25,
            "Android Nougat is the seventh major version and 14th original version of the Android operating system.",
            "N"
        )
        val oreo = OsVersion(
            "Oreo",
            "Version 8.0",
            2018,
            27,
            "Android Oreo 8.0 is the eighth major update to the Android operating system that contains newer features and enhancements for application developers.",
            "O"
        )
//        for (i in 1..10) {
//            osList.add(
//                OsVersion(
//                    "Oreo",
//                    "Version 8",
//                    2018,
//                    28,
//                    "Android Oreo 8.0 is the eighth major update to the Android operating system that contains newer features and enhancements for application developers.",
//                    "O"
//                )
//            )
//        }
        osList.addAll(
            arrayOf(
                angelCake,
                battenBerg,
                cupcake,
                donut,
                eclair,
                froyo,
                gingerBread,
                honeyComb,
                iceCreamSandwich,
                jellyBean,
                kitkat,
                lollipop,
                marshmallow,
                nougat,
                oreo
            )
        )
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