package dev.acuon.sessions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.listener.ClickListener
import dev.acuon.sessions.ui.model.Dog
import dev.acuon.sessions.utils.Extensions.gone

class RandomImageAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<RandomImageAdapter.ImageViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.random_breed_item_layout,
                    parent,
                    false
                ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ImageViewHolder(itemView: View, private val clickListener: ClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val dogImage = itemView.findViewById<ImageView>(R.id.imageSlide)!!
        private val dogName = itemView.findViewById<TextView>(R.id.randomBreedName)!!
        fun setData(dog: Dog) {
            itemView.apply {
                Picasso.get().load(dog.url).into(dogImage)
                dogName.text = dog.name
                setOnClickListener {
                    clickListener.onClick(adapterPosition)
                }
            }
        }
    }
}