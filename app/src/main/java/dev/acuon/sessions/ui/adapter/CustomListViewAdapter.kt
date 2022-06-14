package dev.acuon.sessions.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import kotlinx.android.synthetic.main.item_layout.view.*

class CustomListViewAdapter(
    private val context: Activity,
    private val osList: ArrayList<OsVersion>
) : ArrayAdapter<OsVersion>(context, R.layout.item_layout, osList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.item_layout, null, true)

        rowView.apply {
            osSymbol.text = osList[position].symbol
            osName.text = osList[position].name
            osVersion.text = osList[position].version
            osSdk.text = "SDK ${osList[position].sdk}"
            osDescription.text = osList[position].description
            osReleaseDate.text = "Released on ${osList[position].releasedOn}"
        }
        return rowView
    }
}