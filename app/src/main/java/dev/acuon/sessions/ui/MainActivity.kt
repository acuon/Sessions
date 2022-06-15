package dev.acuon.sessions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityMainBinding
import dev.acuon.sessions.utils.Constants

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
                snackBar(Constants.SIMPLE_DIALOG)
                simpleDialog()
            }
            R.id.simpleDialogWithButton -> {
                snackBar(Constants.DIALOG_WITH_BUTTONS)
                dialogWithButtons()
            }
            R.id.singleChoiceDialog -> {
                snackBar(Constants.SINGLE_CHOICE_DIALOG)
            }
            R.id.multiChoiceDialog -> {
                snackBar(Constants.MULTI_CHOICE_DIALOG)
            }
            R.id.circularProgressDialog -> {
                snackBar(Constants.CIRCULAR_PROGRESS_DIALOG)
            }
            R.id.datePickerDialog -> {
                snackBar(Constants.DATE_PICKER_DIALOG)
            }
            R.id.timePickerDialog -> {
                snackBar(Constants.TIME_PICKER_DIALOG)
            }
            R.id.bottomDialog -> {
                snackBar(Constants.BOTTOM_DIALOG)
            }
            R.id.customDialog -> {
                snackBar(Constants.CUSTOM_DIALOG)
            }
        }
    }

    private fun simpleDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("This is Simple Dialog")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->
                snackBar("Ok")
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
                snackBar("Yes")
            }
            .setNeutralButton("Cancel") { _, _ ->
                snackBar("Cancel")
            }
            .setNegativeButton("No") { _, _ ->
                snackBar("No")
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Dialog With Buttons")
        alert.show()
    }

    private fun singleChoiceDialog() {

    }

    private fun snackBar(str: String) {
        Snackbar.make(binding.mainLayoutCoordinator, "$str clicked", Snackbar.LENGTH_SHORT).show()
    }
}