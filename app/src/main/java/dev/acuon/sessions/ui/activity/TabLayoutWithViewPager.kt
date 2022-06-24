package dev.acuon.sessions.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayoutMediator
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityTabLayoutWithViewPagerBinding
import dev.acuon.sessions.ui.adapter.TabAdapter
import dev.acuon.sessions.utils.ActivityUtils
import dev.acuon.sessions.utils.Constants.CALLS
import dev.acuon.sessions.utils.Constants.CHATS
import dev.acuon.sessions.utils.Constants.STATUS

class TabLayoutWithViewPager : AppCompatActivity() {
    private lateinit var binding: ActivityTabLayoutWithViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutWithViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
    }

    private fun setAdapter() {
        val adapter = TabAdapter(supportFragmentManager, lifecycle)
        binding.apply {
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = CHATS
                    1 -> tab.text = STATUS
                    2 -> tab.text = CALLS
                }
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ActivityUtils.intent(item.itemId, this)
        return true
    }

}