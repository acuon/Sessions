package dev.acuon.sessions.ui.postdetails


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.postdetails.model.CommentItem
import dev.acuon.sessions.utils.Extensions.hide
import dev.acuon.sessions.utils.Extensions.show

class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<CommentItem>() {
        override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(comment: CommentItem) {
            itemView.apply {
                findViewById<TextView>(R.id.commentBody).text = comment.body
                findViewById<TextView>(R.id.commentName).text = comment.name.split(" ")[0]
                findViewById<TextView>(R.id.commentEmail).text = comment.email
                if(adapterPosition%2==1) {
                    setBackgroundColor(resources.getColor(R.color.grey))
                } else {
                    setBackgroundColor(resources.getColor(R.color.black))
                }
                itemView.setOnClickListener {
                    if(it.visibility == View.INVISIBLE) {
                        show()
                    } else {
                        hide()
                    }
                }
            }
        }
    }
}