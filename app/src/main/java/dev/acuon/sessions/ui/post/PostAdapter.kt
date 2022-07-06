package dev.acuon.sessions.ui.post


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.acuon.sessions.R
import dev.acuon.sessions.ui.post.model.user.User
import dev.acuon.sessions.ui.listener.ClickListener
import dev.acuon.sessions.ui.post.model.PostItem

class PostAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val differCallbackPosts = object : DiffUtil.ItemCallback<PostItem>() {
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem == newItem
        }
    }
    private val differCallbackUsers = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val postsList = AsyncListDiffer(this, differCallbackPosts)
    val usersList = AsyncListDiffer(this, differCallbackUsers)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postsList.currentList[position]
        val user = usersList.currentList[post.userId-1]
        holder.setData(post, user)
    }

    override fun getItemCount(): Int {
        return postsList.currentList.size
    }

    inner class ViewHolder(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {
        fun setData(post: PostItem, user: User) {
            itemView.apply {
                findViewById<TextView>(R.id.userName).text = "~ ${user.username}"
                findViewById<TextView>(R.id.postTitle).text = post.title
                findViewById<TextView>(R.id.postBody).text = post.body
                setOnClickListener {
                    clickListener.onPostClick(adapterPosition)
                }
            }
        }
    }
}