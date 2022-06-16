package dev.acuon.sessions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityMainBinding
import dev.acuon.sessions.databinding.BottomDialogBinding
import dev.acuon.sessions.databinding.CustomDialogBinding
import dev.acuon.sessions.databinding.ProgressDialogBinding
import dev.acuon.sessions.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDialogs()
    }

    private fun setDialogs() {
        binding.apply {
            with(simpleDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(simpleDialogWithButton) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(singleChoiceDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(multiChoiceDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(circularProgressDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(datePickerDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(timePickerDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(bottomDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
            with(customDialog) {
                setBackgroundColor(Constants.getRandomColor())
                setOnClickListener(listener)
            }
        }
    }

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.simpleDialog -> {
                toast(Constants.SIMPLE_DIALOG)
                simpleDialog()
            }
            R.id.simpleDialogWithButton -> {
                toast(Constants.DIALOG_WITH_BUTTONS)
                dialogWithButtons()
            }
            R.id.singleChoiceDialog -> {
                toast(Constants.SINGLE_CHOICE_DIALOG)
                singleChoiceDialog()
            }
            R.id.multiChoiceDialog -> {
                toast(Constants.MULTI_CHOICE_DIALOG)
                multiChoiceDialog()
            }
            R.id.circularProgressDialog -> {
                toast(Constants.CIRCULAR_PROGRESS_DIALOG)
                circularProgressDialog()
            }
            R.id.datePickerDialog -> {
                toast(Constants.DATE_PICKER_DIALOG)
                datePickerDialog()
            }
            R.id.timePickerDialog -> {
                toast(Constants.TIME_PICKER_DIALOG)
                timePickerDialog()
            }
            R.id.bottomDialog -> {
                toast(Constants.BOTTOM_DIALOG)
                bottomDialog()
            }
            R.id.customDialog -> {
                toast(Constants.CUSTOM_DIALOG)
                customDialog()
            }
        }
    }

    private fun simpleDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("This is Simple Dialog")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->
                toast("Ok")
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Simple Dialog")
        alert.show()
    }

    private fun dialogWithButtons() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("This is Dialog With Buttons")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                toast("Yes")
            }
            .setNeutralButton("Cancel") { _, _ ->
                toast("Cancel")
            }
            .setNegativeButton("No") { _, _ ->
                toast("No")
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Dialog With Buttons")
        alert.show()
    }

    private fun singleChoiceDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder
            .setCancelable(false)
            .setSingleChoiceItems(Constants.OPTIONS_ARRAY, -1) { dialogInterface, i ->
                toast("${Constants.OPTIONS_ARRAY[i]} selected")
                dialogInterface.dismiss()
            }
            .setNeutralButton("Cancel") { _, _ ->
                toast("Cancel")
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Single Choice Dialog")
        alert.show()
    }

    private fun multiChoiceDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder
            .setCancelable(false)
            .setMultiChoiceItems(
                Constants.COLORS_ARRAY,
                Constants.CHECKED_COLORS_ARRAY
            ) { dialog, which, isChecked ->
                Constants.CHECKED_COLORS_ARRAY[which] = isChecked
            }
            .setPositiveButton("Ok") { _, _ ->
                var colors = ""
                for (i in Constants.CHECKED_COLORS_ARRAY.indices) {
                    val checked = Constants.CHECKED_COLORS_ARRAY[i]
                    if (checked) {
                        colors += Constants.COLORS_ARRAY[i] + " "
                    }
                }
                toast("$colors selected")
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Multi Choice Dialog")
        alert.show()
    }

    private fun circularProgressDialog() {
        val inflate = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null)
        val progressDialogBinding = ProgressDialogBinding.bind(inflate)
        val dialog = Dialog(this)
        dialog.apply {
            setContentView(progressDialogBinding.root)
            setCancelable(false)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
        progressDialogBinding.apply {
            buttonCancel.setOnClickListener {
                toast("${Constants.CIRCULAR_PROGRESS_DIALOG} dismissed")
                dialog.dismiss()
            }
        }
    }

    private fun datePickerDialog() {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                toast("${sdf.format(cal.time)} date saved")
            }

        DatePickerDialog(
            this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun timePickerDialog() {
        val timePicker: TimePickerDialog
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        timePicker = TimePickerDialog(
            this,
            { _, hourTime, minuteTime ->
                val timePeriod = if (hourTime >= 12) "PM" else "AM"
                toast("${String.format("%d : %d", hourTime % 12, minuteTime)} $timePeriod saved")
            },
            hour,
            minute,
            false
        )
        timePicker.show()
    }

    private fun bottomDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
        val bottomDialogBinding = BottomDialogBinding.bind(view)
        bottomSheetDialog.apply {
            setContentView(bottomDialogBinding.root)
            setCancelable(true)
            window?.attributes!!.windowAnimations = R.style.DialogAnimation
            show()
        }

        bottomDialogBinding.ivClose.setOnClickListener {
            toast("bottom dialog dismissed")
            bottomSheetDialog.cancel()
        }
    }

    private fun customDialog() {
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        val customDialogBinding = CustomDialogBinding.bind(view)
        val customDialog = AlertDialog.Builder(this).create()
        customDialog.apply {
            setView(customDialogBinding.root)
            setCancelable(false)
            show()
        }
        with(customDialogBinding) {
            ivClose.setOnClickListener {
                toast("custom dialog dismissed")
                customDialog.dismiss()
            }
            btnSave.setOnClickListener {
                toast("${editText.text.toString()} saved")
                customDialog.dismiss()
            }
        }
    }

    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}