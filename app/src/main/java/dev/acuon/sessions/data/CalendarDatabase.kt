package dev.acuon.sessions.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.acuon.sessions.model.Reminder

@Database(entities = [Reminder::class], version = 2)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun getDao(): CalendarDao

    companion object {
        private var INSTANCE: CalendarDatabase? = null

        fun getDataBaseObject(context: Context): CalendarDatabase {
            return if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "db_name"
                )
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
                INSTANCE!!
            } else INSTANCE!!
        }
    }
}