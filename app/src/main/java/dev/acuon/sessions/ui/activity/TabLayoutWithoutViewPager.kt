package dev.acuon.sessions.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityTabLayoutWithoutViewPagerBinding
import dev.acuon.sessions.ui.fragment.FragmentCalls
import dev.acuon.sessions.ui.fragment.FragmentChats
import dev.acuon.sessions.ui.fragment.FragmentStatus
import dev.acuon.sessions.utils.Constants.CALLS
import dev.acuon.sessions.utils.Constants.CHATS
import dev.acuon.sessions.utils.Constants.STATUS
import dev.acuon.sessions.utils.ActivityUtils

class TabLayoutWithoutViewPager : AppCompatActivity() {
    private lateinit var binding: ActivityTabLayoutWithoutViewPagerBinding
    private lateinit var fragmentChats: FragmentChats
    private lateinit var fragmentStatus: FragmentStatus
    private lateinit var fragmentCalls: FragmentCalls
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutWithoutViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindWidgetsWithAnEvent()
        setupTabLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ActivityUtils.intent(item.itemId, this)
        return true
    }

    private fun setupTabLayout() {
        fragmentChats = FragmentChats()
        fragmentStatus = FragmentStatus()
        fragmentCalls = FragmentCalls()
        binding.tabLayout.apply {
            addTab(binding.tabLayout.newTab().setText(CHATS), true)
            addTab(binding.tabLayout.newTab().setText(STATUS))
            addTab(binding.tabLayout.newTab().setText(CALLS))
        }
    }

    private fun bindWidgetsWithAnEvent() {
        binding.tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setCurrentTabFragment(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setCurrentTabFragment(tabPosition: Int) {
        when (tabPosition) {
            0 -> replaceFragment(fragmentChats)
            1 -> replaceFragment(fragmentStatus)
            2 -> replaceFragment(fragmentCalls)
        }
    }

    private fun replaceFragment(fragment: Fragment?) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment!!
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}