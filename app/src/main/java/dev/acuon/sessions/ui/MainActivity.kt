package dev.acuon.sessions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityMainBinding
import dev.acuon.sessions.databinding.AddUpdateOsLayoutBinding
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.Adapter
import dev.acuon.sessions.ui.adapter.ClickListener
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ClickListener {
    private lateinit var osAdapter: Adapter
    private lateinit var list: ArrayList<OsVersion>
    private lateinit var binding: ActivityMainBinding
    private lateinit var addUpdateOsDialog: AlertDialog

    companion object {
        const val ADD = 0
        const val UPDATE = 1
        var INDEX = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
        with(binding) {
            addNewOs.setOnClickListener {
                addUpdateNewOs(ADD)
            }
            addNewOs.setOnLongClickListener {
                list.clear()
                list.addAll(Utils.dummyData())
                osAdapter.notifyDataSetChanged()
                updateUI()
                true
            }
        }
    }

    private fun initialize() {
        list = ArrayList()
        osAdapter = Adapter(this)
        binding.apply {
            with(recyclerView) {
                adapter = osAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
        osAdapter.differ.submitList(list)
        updateUI()
    }

    private fun addUpdateNewOs(type: Int) {
        val view = layoutInflater.inflate(R.layout.add_update_os_layout, null)
        val addUpdateOsLayoutBinding = AddUpdateOsLayoutBinding.bind(view)
        addUpdateOsDialog = AlertDialog.Builder(this).create()
        addUpdateOsDialog.apply {
            setView(addUpdateOsLayoutBinding.root)
            window?.attributes!!.windowAnimations = R.style.DialogAnimation
            setCancelable(false)
            show()
        }
        setSpinner(addUpdateOsLayoutBinding)
        if (type == UPDATE) {
            setValuesToDialog(addUpdateOsLayoutBinding)
            addUpdateOsLayoutBinding.addUpdateButton.text = Utils.UPDATE
        } else {
            addUpdateOsLayoutBinding.addUpdateButton.text = Utils.ADD
        }
        with(addUpdateOsLayoutBinding) {
            etOsReleasedOn.setOnClickListener {
                datePickerDialog(etOsReleasedOn, type)
            }
            addUpdateButton.setOnClickListener {
                if (editTextEmptyCheck(addUpdateOsLayoutBinding)) {
                    addUpdateOsLayoutBinding.let {
                        if (type == UPDATE) {
                            with(list[INDEX]) {
                                name = it.etOsName.text.toString()
                                version = spinnerOsVersion.selectedItemPosition + 1
                                releasedOn =
                                    it.etOsReleasedOn.text.toString().split("-").map { it.toInt() }
                                        .toTypedArray()
                                sdk = spinnerOsSdk.selectedItemPosition + 1
                                description = it.etOsDescription.text.toString()
                            }
                        } else {
                            val newOs = OsVersion(
                                it.etOsName.text.toString(),
                                it.spinnerOsVersion.selectedItemPosition + 1,
                                it.etOsReleasedOn.text.toString().split("-").map { it.toInt() }
                                    .toTypedArray(),
                                it.spinnerOsSdk.selectedItemPosition + 1,
                                it.etOsDescription.text.toString()
                            )
                            list.add(newOs)
                        }
                        osAdapter.notifyDataSetChanged()
                    }
                    addUpdateOsDialog.dismiss()
                    updateUI()
                }
            }
            closeDialog.setOnClickListener {
                addUpdateOsDialog.dismiss()
            }
        }
    }

    private fun setSpinner(addUpdateOsLayoutBinding: AddUpdateOsLayoutBinding) {
        //version
        val versionAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            Utils.osVersions()
        )
        versionAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        addUpdateOsLayoutBinding.spinnerOsVersion.apply {
            this.adapter = versionAdapter
            this.prompt = Utils.SPINNER_VERSION_TAG
        }

        //sdk
        val sdkAdapter: ArrayAdapter<Int> = ArrayAdapter<Int>(
            this,
            android.R.layout.simple_spinner_item,
            Utils.osSdk()
        )
        sdkAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        addUpdateOsLayoutBinding.spinnerOsSdk.apply {
            this.adapter = sdkAdapter
            this.prompt = Utils.SPINNER_SDK_TAG
        }
    }

    private fun datePickerDialog(etOsReleasedOn: EditText, type: Int) {
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = Utils.DATE_FORMAT
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                etOsReleasedOn.setText(sdf.format(cal.time))
            }

        DatePickerDialog(
            this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setValuesToDialog(addUpdateOsLayoutBinding: AddUpdateOsLayoutBinding) {
        list[INDEX].let {
            with(addUpdateOsLayoutBinding) {
                etOsName.setText(it.name)
                spinnerOsVersion.setSelection(it.version - 1)
                etOsReleasedOn.setText(it.releasedOn.joinToString("-"))
                spinnerOsSdk.setSelection(it.sdk - 1)
                etOsDescription.setText(it.description)
            }
        }
    }

    private fun editTextEmptyCheck(addUpdateOsLayoutBinding: AddUpdateOsLayoutBinding): Boolean {
        var boolean = true
        with(addUpdateOsLayoutBinding) {
            etOsName.let {
                if (it.text.toString().isEmpty()) {
                    it.error = Utils.NAME_ERROR
                    boolean = false
                }
            }
            etOsReleasedOn.let {
                if (it.text.toString().isEmpty()) {
                    it.error = Utils.RELEASE_DATE_ERROR
                    boolean = false
                }
            }
            etOsDescription.let {
                if (it.text.toString().isEmpty()) {
                    it.error = Utils.DESCRIPTION_ERROR
                    boolean = false
                }
            }
        }
        return boolean
    }

    private fun showDeleteAlert(deleteFromList: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(Utils.DELETE_MESSAGE)
            .setCancelable(false)
            .setPositiveButton(Utils.YES) { _, _ ->
                deleteFromList()
            }
            .setNegativeButton(Utils.NO) { dialogView, _ ->
                dialogView.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(Utils.DELETE)
        alert.show()
    }

    private fun onDeleteClicked() {
        list.removeAt(INDEX).let {
            snackBar("${it.version} ${it.name}")
        }
        osAdapter.notifyItemRemoved(INDEX)
        updateUI()
    }

    private fun updateUI() {
        binding.apply {
            if (list.isEmpty()) emptyState.visibility = View.VISIBLE
            else emptyState.visibility = View.GONE
        }
    }

    override fun onClick(position: Int) {
        INDEX = position
        addUpdateNewOs(UPDATE)
    }

    override fun onDeleteClick(position: Int) {
        INDEX = position
        showDeleteAlert(::onDeleteClicked)
    }

    private fun snackBar(str: String) {
        Snackbar.make(binding.coordinator, "Android $str deleted", Snackbar.LENGTH_SHORT).show()
    }
}