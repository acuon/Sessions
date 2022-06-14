package dev.acuon.sessions.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.CustomListViewAdapter
import dev.acuon.sessions.utils.Constants
import kotlinx.android.synthetic.main.activity_custom_list_view.*

class CustomListViewActivity : AppCompatActivity() {

    private lateinit var osList: ArrayList<OsVersion>
    private lateinit var customListViewAdapter: CustomListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_list_view)
        initialize()
        customListView.setOnItemClickListener { adapterView, view, position, id ->
            snackBar(osList[position].name)
        }
    }

    private fun initialize() {
        supportActionBar?.title = Constants.CUSTOM_LISTVIEW
        osList = ArrayList()
        osList.addAll(Constants.dummyData())
        customListViewAdapter = CustomListViewAdapter(this, osList)
        customListView.adapter = customListViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var activity: AppCompatActivity = CustomListViewActivity()
        var activityName = Constants.CUSTOM_LISTVIEW
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
        if (activityName != Constants.CUSTOM_LISTVIEW) {
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
        Snackbar.make(customListViewCoordinator, "$str clicked", Snackbar.LENGTH_LONG).show()
    }
}