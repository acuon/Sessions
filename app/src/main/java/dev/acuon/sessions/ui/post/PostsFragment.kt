package dev.acuon.sessions.ui.post

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
import dev.acuon.sessions.databinding.FragmentPostsBinding
import dev.acuon.sessions.ui.postdetails.CommentsFragment
import dev.acuon.sessions.ui.post.model.user.User
import dev.acuon.sessions.ui.listener.ClickListener
import dev.acuon.sessions.ui.listener.MainActivityInterface
import dev.acuon.sessions.ui.post.model.PostItem
import dev.acuon.sessions.ui.post.viewmodel.PostViewModel
import dev.acuon.sessions.ui.post.viewmodel.PostViewModelFactory
import dev.acuon.sessions.utils.Extensions.gone
import dev.acuon.sessions.utils.Extensions.show
import dev.acuon.sessions.utils.Extensions.showToast
import dev.acuon.sessions.utils.NetworkUtils

class PostsFragment : Fragment(), ClickListener {
    private lateinit var binding: FragmentPostsBinding
    private var postsList = arrayListOf<PostItem>()
    private var usersList = arrayListOf<User>()
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModel: PostViewModel
    private lateinit var mainActivityInterface: MainActivityInterface
    private lateinit var connectionLiveData: NetworkUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            initNavigationInterface(it)
        }
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initNavigationInterface(context: FragmentActivity) {
        mainActivityInterface = context as MainActivityInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionLiveData = NetworkUtils(activity?.applicationContext!!)
        connectionLiveData.observe(requireActivity()) { connection ->
            binding.apply {
                if (connection!!.isConnected && postsList.isEmpty()) {
                    postRecyclerView.show()
                    networkError.gone()
                    mainActivityInterface.progressBar(true)
                    initialize()
                } else if (postsList.isEmpty()) {
                    postRecyclerView.gone()
                    networkError.show()
                } else if(connection.isConnected) {
                    requireContext().showToast("Internet connected")
                } else {
                    requireContext().showToast("Internet not connected")
                }
            }
        }
    }

    private fun initialize() {
        initViewModel()
        initAdapter()
        getData()
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as ApplicationClass).repository
        viewModel =
            ViewModelProvider(this, PostViewModelFactory(repository))[PostViewModel::class.java]
    }

    private fun initAdapter() {
        postAdapter = PostAdapter(this)
        binding.apply {
            postRecyclerView.adapter = postAdapter
            postRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        postAdapter.postsList.submitList(postsList)
        postAdapter.usersList.submitList(usersList)
    }

    private fun getData() {
        postsList.clear()
        if (usersList.isEmpty()) {
            viewModel.users.observe(viewLifecycleOwner, Observer {
                usersList.addAll(it)
            })
        }
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            postsList.addAll(it)
            mainActivityInterface.progressBar(false)
            postAdapter.notifyDataSetChanged()
        })
    }

    override fun onPostClick(position: Int) {
        val fragment = CommentsFragment()
        val bundle = Bundle().apply {
            putSerializable("post", postsList[position])
            putSerializable("user", usersList[postsList[position].userId - 1])
            putInt("userId", postsList[position].userId)
            putInt("postId", postsList[position].id)
        }
        fragment.arguments = bundle
        mainActivityInterface.openFragment(fragment)
    }
}