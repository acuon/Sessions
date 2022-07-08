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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.FragmentBreedImagesBinding
import dev.acuon.sessions.ui.adapter.DogAdapter
import dev.acuon.sessions.ui.adapter.DogImageAdapter
import dev.acuon.sessions.ui.listener.ClickListener
import dev.acuon.sessions.ui.listener.MainActivityInterface
import dev.acuon.sessions.utils.Extensions.gone
import dev.acuon.sessions.utils.Extensions.showToast
import dev.acuon.sessions.utils.NetworkUtils
import dev.acuon.sessions.viewmodel.MainViewModel
import dev.acuon.sessions.viewmodel.MainViewModelFactory

class BreedImages : Fragment(), ClickListener {
    private lateinit var binding: FragmentBreedImagesBinding
    private lateinit var mainActivityInterface: MainActivityInterface
    private lateinit var viewModel: MainViewModel
    private lateinit var breedName: String
    private lateinit var listOfBreedImage: ArrayList<String>
    private lateinit var connectionLiveData: NetworkUtils
    private lateinit var dogImageAdapter: DogImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            breedName = it.getString("name")!!
        }
        activity?.let {
            initNavigationInterface(it)
        }
        binding = FragmentBreedImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initNavigationInterface(context: FragmentActivity) {
        mainActivityInterface = context as MainActivityInterface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().showToast(breedName)
        mainActivityInterface.setFragmentName(breedName)
        connectionLiveData = NetworkUtils(activity?.applicationContext!!)
        connectionLiveData.observe(requireActivity()) { connection ->
            binding.apply {
                if (connection!!.isConnected) {
                    mainActivityInterface.progressBar(true)
                    initialize()
                } else {
                    requireContext().showToast("Internet not connected")
                }
            }
        }
    }

    private fun initialize() {
        setAdapter()
        initViewModel()
        getData()
    }

    private fun setAdapter() {
        listOfBreedImage = ArrayList()
        dogImageAdapter = DogImageAdapter(this)
        dogImageAdapter.differ.submitList(listOfBreedImage)
        binding.rcv.apply {
            adapter = dogImageAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as ApplicationClass).repository
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository, breedName)
        )[MainViewModel::class.java]
    }

    private fun getData() {
        listOfBreedImage.clear()
        viewModel.breedImages.observe(viewLifecycleOwner, Observer {
            mainActivityInterface.progressBar(false)
            listOfBreedImage.addAll(it.message)
            dogImageAdapter.notifyDataSetChanged()
        })
    }

    override fun onClick(position: Int) {
        requireContext().showToast("clicked")
    }

    override fun onDestroy() {
        super.onDestroy()
        arguments?.let {
            val previous = it.getString("previous")
            if(previous == AllBreeds::class.java.name) {
                mainActivityInterface.setFragmentName("Dog Breeds")
            } else if(previous == RandomBreeds::class.java.name){
                mainActivityInterface.setFragmentName("Random Breeds")
            }
        }
    }
}