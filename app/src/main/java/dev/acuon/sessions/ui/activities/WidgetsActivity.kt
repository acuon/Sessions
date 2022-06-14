package dev.acuon.sessions.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.R
import dev.acuon.sessions.utils.Constants
import kotlinx.android.synthetic.main.activity_widgets.*

class WidgetsActivity : AppCompatActivity() {

    private var jumpTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets)
        supportActionBar?.title = Constants.WIDGETS

        setSpinner()
        progressBar()
        clickListener()
    }

    private fun clickListener() {
        //Material Buttons
        textButton.setOnClickListener {
            snackBar("Text Button")
        }
        outlinedButton.setOnClickListener {
            snackBar("Outlined Button")
        }
        materialButtonRounded.setOnClickListener {
            snackBar("Rounded Material Button ")
        }
        materialButton.setOnClickListener {
            snackBar("Material Button")
        }
        materialButtonWithIcon.setOnClickListener {
            snackBar("Material Button With Icon")
        }

        //Toggle Button
        toggleButton.setOnClickListener {
            snackBar("Toggle Button")
        }

        //Chips
        checkableChip.setOnClickListener {
            snackBar("Checkable Chip")
        }
        strokeChip.setOnClickListener {
            snackBar("Stroke Chip")
        }
        cornerRadiusChip.setOnClickListener {
            snackBar("Corner Radius Chip")
        }
        iconChip.setOnClickListener {
            snackBar("Icon Chip")
        }
        entryChip.setOnClickListener {
            snackBar("Entry Chip")
        }
        entryChip.setOnCloseIconClickListener {
            snackBar("Entry Chip Close Icon")
        }

        //switch
        simpleSwitch.setOnClickListener {
            snackBar("Switch")
        }

        //Text Input Layout
        editText.setOnClickListener {
            snackBar("Edit Text")
        }

        //Check Box
        checkBox1.setOnClickListener {
            snackBar("Check Box 1")
        }
        checkBox2.setOnClickListener {
            snackBar("Check Box 2")
        }
        checkBox3.setOnClickListener {
            snackBar("Check Box 3")
        }

        //Radio Buttons
        radioButton1.setOnClickListener {
            snackBar("Radio Button 1")
        }
        radioButton2.setOnClickListener {
            snackBar("Radio Button 2")
        }
        radioButton3.setOnClickListener {
            snackBar("Radio Button 3")
        }

        //Seek Bar
        simpleSeekBar.setOnClickListener {
            snackBar("Seek Bar")
        }
        simpleSeekBar.setOnTouchListener { _, _ ->
            snackBar("Seek Bar")
            false
        }

        //Progress Bar
        progressBar.setOnClickListener {
            snackBar("Progress Bar")
        }

        //Spinner
        spinner.setOnTouchListener { _, _ ->
            snackBar("Spinner")
            false
        }
    }

    private fun setSpinner() {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            Constants.osNames
        )
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter
    }

    private fun progressBar() {
        val totalProgressTime = 100
        val t: Thread = object : Thread() {
            override fun run() {
                jumpTime = 0
                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200)
                        jumpTime += 10
                        progressBar.progress = jumpTime
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if(jumpTime >= totalProgressTime) {
                        jumpTime = 0;
                    }
                }
            }
        }
        t.start()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var activity: AppCompatActivity = WidgetsActivity()
        var activityName = Constants.WIDGETS
        when (item.itemId) {
            R.id.widgets -> {
                activity = WidgetsActivity()
                activityName = Constants.WIDGETS
            }
            R.id.listView -> {
                activity = ListViewActivity()
                activityName = Constants.LISTVIEW
            }
            R.id.recyclerView -> {
                activity = RecyclerViewActivity()
                activityName = Constants.RECYCLER_VIEW
            }
            R.id.customListView -> {
                activity = CustomListViewActivity()
                activityName = Constants.CUSTOM_LISTVIEW
            }
        }
        val intent = Intent(this, activity::class.java)
        if (activityName != Constants.WIDGETS) {
            startActivity(intent)
            finish()
        }
        toast(activityName)
        return true
    }

    private fun toast(str: String) {
        Toast.makeText(this, "$str clicked", Toast.LENGTH_SHORT).show()
    }

    private fun snackBar(str: String) {
        Snackbar.make(widgetsCoordinator, "$str clicked", Snackbar.LENGTH_LONG).show()
    }

}