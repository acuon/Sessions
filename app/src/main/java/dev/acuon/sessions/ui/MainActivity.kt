package dev.acuon.sessions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.OsAdapter
import dev.acuon.sessions.ui.fragments.CustomListViewFragment
import dev.acuon.sessions.ui.fragments.ListViewFragment
import dev.acuon.sessions.ui.fragments.RecyclerViewFragment
import dev.acuon.sessions.ui.fragments.WidgetsFragment
import dev.acuon.sessions.utils.Constants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        openFragment(WidgetsFragment())
        supportActionBar?.title = Constants.WIDGETS
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment = WidgetsFragment()
        var fragmentName = Constants.WIDGETS
        when (item.itemId) {
            R.id.widgets -> {
                fragment = WidgetsFragment()
                fragmentName = Constants.WIDGETS
                toast(fragmentName)
            }
            R.id.listView -> {
                fragment = ListViewFragment()
                fragmentName = Constants.LISTVIEW
                toast(fragmentName)
            }
            R.id.recyclerView -> {
                fragment = RecyclerViewFragment()
                fragmentName = Constants.RECYCLER_VIEW
                toast(fragmentName)
            }
            R.id.customListView -> {
                fragment = CustomListViewFragment()
                fragmentName = Constants.CUSTOM_LISTVIEW
                toast(fragmentName)
            }
        }
        supportActionBar?.title = fragmentName
        openFragment(fragment)
        return true
    }

    private fun toast(str: String) {
        Toast.makeText(this, "$str clicked", Toast.LENGTH_SHORT).show()
    }
}