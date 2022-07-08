package dev.acuon.sessions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.listener.ClickListener

class DogImageAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<DogImageAdapter.ViewHolder>() {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item_layout, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return /*differ.currentList.size*/ 20
    }

    inner class ViewHolder(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {
        fun setData(url: String) {
            itemView.apply {
//                Picasso.get().load(url).into(findViewById<ImageView>(R.id.dogImage))
                Glide.with(this).load(url).into(findViewById<ImageView>(R.id.dogImage))
                setOnClickListener {
                    clickListener.onClick(adapterPosition)
                }
            }
        }
    }
}