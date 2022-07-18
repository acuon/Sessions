package dev.acuon.sessions.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.R
import dev.acuon.sessions.utils.Extensions.toast
import dev.acuon.sessions.databinding.ActivityMainBinding
import dev.acuon.sessions.databinding.AddReminderLayoutBinding
import dev.acuon.sessions.model.Day
import dev.acuon.sessions.model.Reminder
import dev.acuon.sessions.utils.Extensions.currentTime
import dev.acuon.sessions.utils.Extensions.monthToArray
import dev.acuon.sessions.utils.Extensions.monthYearFromDate
import dev.acuon.sessions.utils.Extensions.setDate
import dev.acuon.sessions.utils.Extensions.setTime
import dev.acuon.sessions.viewmodel.CalendarVM
import dev.acuon.sessions.viewmodel.CalendarVMFactory
import java.time.LocalDate
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class CalendarActivity : AppCompatActivity(), DateClickListener, View.OnClickListener,
    ReminderClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var addTaskLayoutBinding: AddReminderLayoutBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var viewModel: CalendarVM

    private var selectedDate: LocalDate? = null
    private lateinit var calendarAdapter: CalendarDaysAdapter
    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var daysInMonth: ArrayList<Day>
    private lateinit var reminderList: ArrayList<Reminder>
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()

        selectedDate = LocalDate.now()
        date = selectedDate.toString()

        setMonthView()
        setReminderRCV()

        binding.fabAddReminder.setOnClickListener(this@CalendarActivity)
    }

    private fun setReminderRCV() {
        reminderAdapter = ReminderAdapter(this)
        reminderList = ArrayList()
        reminderAdapter.reminderList.submitList(reminderList)
        binding.reminderRecyclerView.apply {
            adapter = reminderAdapter
            layoutManager = LinearLayoutManager(this@CalendarActivity)
        }
        getReminderByDate(selectedDate.toString())
    }

    private fun getReminderByDate(date: String) {
        viewModel.getReminderByDate(date).observe(this, androidx.lifecycle.Observer {
            reminderList.clear()
            reminderList.addAll(it)
            reminderAdapter.notifyDataSetChanged()
        })
    }

    private fun initViewModel() {
        val repository = (application as ApplicationClass).repository
        viewModel = ViewModelProvider(
            this,
            CalendarVMFactory(repository)
        )[CalendarVM::class.java]
    }

    private fun setMonthView() {
        previousMonth()
        nextMonth()
        val currentDate = selectedDate.toString()
//        daysInMonth = monthToArray(selectedDate!!)
        daysInMonth = selectedDate!!.monthToArray()
        calendarAdapter = CalendarDaysAdapter(daysInMonth, this, currentDate, this, viewModel)
        val layoutManager = GridLayoutManager(this, 7)

        binding.apply {
//            monthYearTV.text = monthYearFromDate(selectedDate!!)
            monthYearTV.text = selectedDate!!.monthYearFromDate()
            calendarRecyclerView.layoutManager = layoutManager
            calendarRecyclerView.adapter = calendarAdapter
        }
        getReminderByDate(selectedDate.toString())
    }

    private fun previousMonth() {
        binding.previousMonth.setOnClickListener {
            selectedDate = selectedDate!!.minusMonths(1)
            setMonthView()
        }
    }

    private fun nextMonth() {
        binding.nextMonth.setOnClickListener {
            selectedDate = selectedDate!!.plusMonths(1)
            setMonthView()
        }
    }

    private fun showBottomSheet(reminder: Reminder? = null) {
        bottomSheetDialog = BottomSheetDialog(this@CalendarActivity)
        addTaskLayoutBinding = AddReminderLayoutBinding.inflate(layoutInflater)

        if (reminder != null) addTaskLayoutBinding.reminder = reminder

        bottomSheetDialog.apply {
            setContentView(addTaskLayoutBinding.root)
            setCancelable(false)
            show()
        }
        addTaskLayoutBinding.apply {
            if(reminder != null) {
                tvReminderDate.text = reminder.date
                tvReminderTime.text = reminder.time
            } else {
                tvReminderDate.text = date
                tvReminderTime.currentTime()
            }
            tvReminderDate.setOnClickListener {
                tvReminderDate.setDate(this@CalendarActivity)
            }
            tvReminderTime.setOnClickListener {
                tvReminderTime.setTime(this@CalendarActivity)
            }
            ivCloseBottomSheet.setOnClickListener {
                bottomSheetDialog.cancel()
            }
            btnSubmit.setOnClickListener {
                viewModel.insertDataInReminder(
                    Reminder(
                        tvReminderTitle.text.toString(),
                        tvReminderDescription.text.toString(),
                        tvReminderDate.text.toString(),
                        tvReminderTime.text.toString(),
                        0,
                    )
                )
                bottomSheetDialog.cancel()
            }
        }
    }

    override fun onDateClicked(today: String, position: Int) {
        date = today
//        this.toast(today)
        getReminderByDate(today)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.fabAddReminder -> {
                    showBottomSheet()
                }
            }
        }
    }

    override fun onLongClick(position: Int) {
        this.toast(reminderList[position].name + " long clicked")
        reminderList[position].showDeleteDialog()
    }

    override fun onClick(position: Int) {
        this.toast(reminderList[position].let { it.name } + " clicked")
        showBottomSheet(reminderList[position])
    }

    private fun Reminder.showDeleteDialog() {
        val dialogBuilder = AlertDialog.Builder(this@CalendarActivity)
        dialogBuilder.setMessage("Delete this Reminder")
            .setCancelable(false)
            .setPositiveButton("yes") { _, _ ->
                // TODO: Yet to implement
            }
            .setNegativeButton("no") { dialogView, _ ->
                dialogView.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.show()
    }
}