package dev.acuon.sessions.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.R
import dev.acuon.sessions.utils.Constants
import kotlinx.android.synthetic.main.activity_list_view.*
import kotlinx.android.synthetic.main.activity_widgets.*

class ListViewActivity : AppCompatActivity() {

    private lateinit var osList: ArrayList<String>
    private lateinit var arrayAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        osList = Constants.osNames
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, osList)
        listView.adapter = arrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var activity: AppCompatActivity = ListViewActivity()
        var activityName = Constants.LISTVIEW
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
        if (activityName != Constants.LISTVIEW) {
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
        Snackbar.make(listViewCoordinator, "$str clicked", Snackbar.LENGTH_LONG).show()
    }
}