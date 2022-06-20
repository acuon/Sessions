package dev.acuon.sessions.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.sessions.R
import dev.acuon.sessions.model.OsVersion
import kotlinx.android.synthetic.main.item_layout.view.*

class Adapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<OsVersion>() {
        override fun areItemsTheSame(oldItem: OsVersion, newItem: OsVersion): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: OsVersion, newItem: OsVersion): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            setData(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View, private val clickListener: ClickListener) :
        RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun setData(os: OsVersion) {
            itemView.apply {
                with(os) {
                    osSymbol.text = (name[0]).toString()
                    osName.text = name
                    osVersion.text = "Android $version"
                    osSdk.text = "SDK $sdk"
                    osDescription.text = description
                    osReleaseDate.text = "Released on ${releasedOn[2]}"
                    setOnClickListener {
                        adapterPosition.let {
                            clickListener.onClick(it)
                        }
                    }
                    ivDelete.setOnClickListener {
                        adapterPosition.let {
                            clickListener.onDeleteClick(it)
                        }
                    }
                }
            }
        }
    }
}