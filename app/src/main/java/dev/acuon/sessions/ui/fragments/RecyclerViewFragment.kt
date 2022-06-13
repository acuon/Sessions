package dev.acuon.sessions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.OsAdapter
import dev.acuon.sessions.ui.adapter.OsClickListener
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view), OsClickListener {

    private lateinit var osList: ArrayList<OsVersion>
    private lateinit var osAdapter: OsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        recyclerView.apply {
            adapter = osAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initialize() {
        osList = ArrayList()
        dummyData()
        osAdapter = OsAdapter(osList, this)
    }

    private fun dummyData() {
        for (i in 1..10) {
            osList.add(
                OsVersion(
                    "Oreo",
                    "Version 8",
                    2018,
                    28,
                    "Android Oreo 8.0 is the eighth major update to the Android operating system that contains newer features and enhancements for application developers."
                )
            )
        }
    }

    override fun onClick(position: Int) {
        Toast.makeText(requireContext(), osList[position].name, Toast.LENGTH_SHORT).show()
    }
}