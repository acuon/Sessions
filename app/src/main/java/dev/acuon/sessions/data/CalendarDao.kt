package dev.acuon.sessions.data

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.acuon.sessions.model.Reminder

@Dao
interface CalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataInReminder(reminder: Reminder)

    @Query("select * from reminders_table where date = :date")
    fun getReminderByDate(date:String):LiveData<List<Reminder>>

    @Delete
    fun deleteFromReminder(reminder: Reminder)
}