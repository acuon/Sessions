package dev.acuon.sessions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import kotlinx.android.synthetic.main.item_layout.view.*

class RecyclerViewAdapter(
    private val list: ArrayList<OsVersion>,
    private val clickListener: OsClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_layout,
                    parent,
                    false
                ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View, private val clickListener: OsClickListener) :
        RecyclerView.ViewHolder(itemView) {
            fun setData(os: OsVersion) {
                itemView.apply {
                    osSymbol.text = os.symbol
                    osName.text = os.name
                    osVersion.text = os.version
                    osSdk.text = "SDK ${os.sdk}"
                    osDescription.text = os.description
                    osReleaseDate.text = "Release on ${os.releasedOn}"
                    setOnClickListener {
                        clickListener.onClick(adapterPosition)
                    }
                }
            }
    }
}