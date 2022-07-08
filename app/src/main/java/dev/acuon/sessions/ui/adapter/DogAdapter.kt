package dev.acuon.sessions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.listener.ClickListener


class DogAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {
        fun setData(name: String) {
            itemView.apply {
//                Picasso.get().load(imageUrl).into(findViewById<ImageView>(R.id.image))
                findViewById<TextView>(R.id.breedName).text = name
                findViewById<TextView>(R.id.breedNameInitials).text = name[0].toString()
                setOnClickListener {
                    clickListener.onClick(adapterPosition)
                }
            }
        }
    }
}