package dev.acuon.sessions.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.acuon.sessions.ui.fragment.FragmentCalls
import dev.acuon.sessions.ui.fragment.FragmentChats
import dev.acuon.sessions.ui.fragment.FragmentStatus


class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return FragmentChats()
            1 -> return FragmentStatus()
            2 -> return FragmentCalls()
        }
        return FragmentChats()
    }
}