package dev.acuon.sessions.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.RecyclerViewAdapter
import dev.acuon.sessions.ui.adapter.OsClickListener
import dev.acuon.sessions.utils.Constants
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.activity_widgets.*

class RecyclerViewActivity : AppCompatActivity(), OsClickListener {

    private lateinit var osList: ArrayList<OsVersion>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initialize()
        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
        }
    }

    private fun initialize() {
        supportActionBar?.title = Constants.RECYCLER_VIEW
        osList = ArrayList()
        osList.addAll(Constants.dummyData())
        recyclerViewAdapter = RecyclerViewAdapter(osList, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var activity: AppCompatActivity = RecyclerViewActivity()
        var activityName = Constants.RECYCLER_VIEW
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
        if(activityName != Constants.RECYCLER_VIEW) {
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
        Snackbar.make(recyclerViewCoordinator, "$str clicked", Snackbar.LENGTH_LONG).show()
    }

    override fun onClick(position: Int) {
        snackBar(osList[position].name)
    }
}