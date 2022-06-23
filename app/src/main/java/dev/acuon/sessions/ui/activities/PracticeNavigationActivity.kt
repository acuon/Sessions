package dev.acuon.sessions.ui.activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityPracticeNavigationBinding

class PracticeNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPracticeNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.app_background_color
                )
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var activity: AppCompatActivity = PracticeNavigationActivity()
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