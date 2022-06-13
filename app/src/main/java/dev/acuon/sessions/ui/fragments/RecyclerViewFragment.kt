package dev.acuon.sessions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.RecyclerViewAdapter
import dev.acuon.sessions.ui.adapter.OsClickListener
import dev.acuon.sessions.utils.Constants
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view), OsClickListener {

    private lateinit var osList: ArrayList<OsVersion>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initialize() {
        osList = ArrayList()
        osList = Constants.dummyData(osList)
        recyclerViewAdapter = RecyclerViewAdapter(osList, this)
    }

    override fun onClick(position: Int) {
        Toast.makeText(requireContext(), osList[position].name, Toast.LENGTH_SHORT).show()
    }
}