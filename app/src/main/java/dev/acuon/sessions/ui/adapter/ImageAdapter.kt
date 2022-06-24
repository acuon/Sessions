package dev.acuon.sessions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dev.acuon.sessions.R
import dev.acuon.sessions.model.Image

class ImageAdapter(private var imageList: ArrayList<Image>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_layout,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.setData(imageList[position])
        if(position == imageList.size-2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(image: Image) {
            itemView.apply {
                findViewById<ImageView>(R.id.imageSlide).setImageResource(image.image)
            }
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}