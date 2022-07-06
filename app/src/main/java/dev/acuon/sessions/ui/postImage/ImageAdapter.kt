package dev.acuon.sessions.ui.postImage

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
import dev.acuon.sessions.ui.postImage.model.PostImageItem
import dev.acuon.sessions.ui.postdetails.model.CommentItem

class ImageAdapter(): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<PostImageItem>() {
        override fun areItemsTheSame(oldItem: PostImageItem, newItem: PostImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostImageItem, newItem: PostImageItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(image: PostImageItem) {
            itemView.apply {
                val imageView = findViewById<ImageView>(R.id.image)/*.setImageResource(R.drawable.ic_launcher_background)*/
//                Glide.with(this).load(image.url+".jpg").into(imageView)
                Picasso.get().load(image.url).into(imageView)
            }
        }
    }
}