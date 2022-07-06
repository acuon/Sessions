package dev.acuon.sessions.ui.postdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.databinding.FragmentCommentsBinding
import dev.acuon.sessions.ui.postdetails.model.CommentItem
import dev.acuon.sessions.ui.post.model.user.User
import dev.acuon.sessions.ui.postdetails.viewmodel.CommentViewModel
import dev.acuon.sessions.ui.postdetails.viewmodel.CommentViewModelFactory
import dev.acuon.sessions.ui.listener.MainActivityInterface
import dev.acuon.sessions.ui.post.model.PostItem
import dev.acuon.sessions.utils.Extensions.gone
import dev.acuon.sessions.utils.Extensions.show
import dev.acuon.sessions.utils.NetworkUtils

class CommentsFragment : Fragment() {
    private lateinit var binding: FragmentCommentsBinding
    private var list = arrayListOf<CommentItem>()
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var viewModel: CommentViewModel
    private lateinit var mainActivityInterface: MainActivityInterface
    private lateinit var connectionLiveData: NetworkUtils
    private lateinit var user: User
    private lateinit var post: PostItem
    private var likes = 0
    private var liked = false

    companion object {
        private var userId = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            userId = it.getInt("userId")
            user = it.getSerializable("user") as User
            post = it.getSerializable("post") as PostItem
        }
        activity?.let {
            initNavigationInterface(it)
        }
        binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initNavigationInterface(context: FragmentActivity) {
        mainActivityInterface = context as MainActivityInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionLiveData = NetworkUtils(activity?.applicationContext!!)
        binding.apply {
            connectionLiveData.observe(requireActivity()) {
                if (it.isConnected && list.isEmpty()) {
                    networkError.gone()
                    nestedScrollView.show()
                    mainActivityInterface.progressBar(true)
                    init()
                } else if(list.isEmpty()) {
                    networkError.show()
                    nestedScrollView.gone()
                }
            }
        }
    }

    private fun init() {
        initViewModel()
        initAdapter()
        getAllComments()
        setPost()
    }

    private fun setPost() {
        binding.apply {
            postTitle.text = post.title
            postBody.text = post.body
            userName.text = "~ ${user.username}"
            postLike.apply {
                setOnClickListener {
                    if (liked) {
                        likes--
                        liked = false
                    } else {
                        likes++
                        liked = true
                    }
                    text = if (likes == 0) {
                        "Like"
                    } else {
                        likes.toString()
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as ApplicationClass).repository
        viewModel =
            ViewModelProvider(
                this,
                CommentViewModelFactory(repository, userId)
            )[CommentViewModel::class.java]
    }

    private fun initAdapter() {
        commentAdapter = CommentAdapter()
        binding.apply {
            commentRecyclerView.adapter = commentAdapter
            commentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        commentAdapter.differ.submitList(list)
    }

    private fun getAllComments() {
        viewModel.comments.observe(viewLifecycleOwner, Observer {
            list.addAll(it)
            mainActivityInterface.progressBar(false)
            commentAdapter.notifyDataSetChanged()
        })
    }
}