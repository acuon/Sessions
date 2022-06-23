package dev.acuon.sessions.ui.activities

import android.R.color
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityBottomNavigationBinding
import dev.acuon.sessions.utils.Constants.COLOR_STATE_LIST


class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding

    //    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.app_background_color
                )
            )
        )
        setBottomNav()
    }

    private fun setBottomNav() {
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        setupWithNavController(binding.bottomNavigationView, navController)

        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.itemIconTintList = COLOR_STATE_LIST
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentHome, R.id.fragmentSearch, R.id.fragmentOrders, R.id.fragmentProfile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var activity: AppCompatActivity = BottomNavigationActivity()
        when (item.itemId) {
            R.id.navigation_activity -> {
                activity = PracticeNavigationActivity()
            }
            R.id.bottom_navigation -> {
                activity = BottomNavigationActivity()
            }
        }
        val intent = Intent(this, activity::class.java)
        if (activity.javaClass.name != this.javaClass.name) {
            startActivity(intent)
            finish()
        }
        return true
    }
}