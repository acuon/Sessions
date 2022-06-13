package dev.acuon.sessions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import dev.acuon.sessions.ui.adapter.CustomListViewAdapter
import dev.acuon.sessions.utils.Constants
import kotlinx.android.synthetic.main.fragment_custom_list_view.*

class CustomListViewFragment : Fragment(R.layout.fragment_custom_list_view) {

    private lateinit var osList: ArrayList<OsVersion>
    private lateinit var customListViewAdapter: CustomListViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        customListView.setOnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(requireContext(), "${osList[position].name} clicked", Toast.LENGTH_SHORT).show()
        }
    }
    private fun initialize() {
        osList = ArrayList()
        osList = Constants.dummyData(osList)
        customListViewAdapter = CustomListViewAdapter(requireActivity(), osList)
        customListView.adapter = customListViewAdapter        
    }
}