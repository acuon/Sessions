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
        entry_chip.setOnCloseIconClickListener {
            snackBar("entry")
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
                        jumpTime += 5
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