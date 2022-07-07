package dev.acuon.sessions.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.databinding.FragmentRandomBreedsBinding
import dev.acuon.sessions.ui.listener.ClickListener
import dev.acuon.sessions.ui.listener.MainActivityInterface
import dev.acuon.sessions.utils.Extensions.randomIndex
import dev.acuon.sessions.utils.Extensions.showToast
import dev.acuon.sessions.utils.NetworkUtils
import dev.acuon.sessions.viewmodel.MainViewModel
import dev.acuon.sessions.viewmodel.MainViewModelFactory

class RandomBreeds : Fragment(), ClickListener {
    private lateinit var binding: FragmentRandomBreedsBinding
    private lateinit var mainActivityInterface: MainActivityInterface
    private lateinit var viewModel: MainViewModel
    private val list by lazy { ArrayList<String>() }
    private lateinit var connectionLiveData: NetworkUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            initNavigationInterface(it)
        }
        binding = FragmentRandomBreedsBinding.inflate(inflater, container, false)
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
                if (connection!!.isConnected) {
                    initialize()
                } else {
                    requireContext().showToast("Internet not connected")
                }
            }
        }
    }

    private fun initialize() {
        initViewModel()
        getData()
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as ApplicationClass).repository
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository, "affenpinscher"))[MainViewModel::class.java]
    }

    private fun getData() {
        viewModel.breeds.observe(viewLifecycleOwner, Observer {
            list.addAll(it)
            Log.d("response", list.toString())
            mainActivityInterface.progressBar(false)
        })

        viewModel.breedImage.observe(viewLifecycleOwner, Observer {
            Log.d("image", it.message)
        })
    }

    override fun onPostClick(position: Int) {
        requireContext().showToast("clicked")
    }
}