package dev.acuon.sessions.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.databinding.FragmentRandomBreedsBinding
import dev.acuon.sessions.ui.adapter.RandomImageAdapter
import dev.acuon.sessions.ui.listener.ClickListener
import dev.acuon.sessions.ui.listener.MainActivityInterface
import dev.acuon.sessions.ui.model.Dog
import dev.acuon.sessions.utils.Extensions.showToast
import dev.acuon.sessions.utils.NetworkUtils
import dev.acuon.sessions.viewmodel.MainViewModel
import dev.acuon.sessions.viewmodel.MainViewModelFactory
import kotlinx.coroutines.*
import kotlin.math.abs


class RandomBreeds : Fragment(), ClickListener {
    private lateinit var binding: FragmentRandomBreedsBinding
    private lateinit var mainActivityInterface: MainActivityInterface
    private lateinit var viewModel: MainViewModel
    private lateinit var connectionLiveData: NetworkUtils
    private lateinit var fiveRandomBreedsList: ArrayList<Dog>
    private lateinit var imageAdapter: RandomImageAdapter

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
        mainActivityInterface.setFragmentName("Random Breeds")
        binding.tvAllBreeds.setOnClickListener {
            mainActivityInterface.openFragment(AllBreeds())
        }
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
        fiveRandomBreedsList = ArrayList()

        val transformer = CompositePageTransformer()
        transformer.apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        }

        binding.viewPagerImageSlider.apply {
            imageAdapter = RandomImageAdapter(this@RandomBreeds)
            imageAdapter.differ.submitList(fiveRandomBreedsList)
            adapter = imageAdapter
            offscreenPageLimit = 2
            clipChildren = false
            clipToPadding = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }
    }

    private fun initViewModel() {
        val repository = (requireActivity().application as ApplicationClass).repository
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]
    }

    private fun getData() {
        fiveRandomBreedsList.clear()
        viewModel.fiveRandomBreeds.observe(viewLifecycleOwner, Observer {
            mainActivityInterface.progressBar(false)
            fiveRandomBreedsList.clear()
            fiveRandomBreedsList.addAll(it)
            Log.d("list", fiveRandomBreedsList.toString())
//            requireContext().showToast(it.toString())
            imageAdapter.notifyDataSetChanged()
        })
    }

    override fun onClick(position: Int) {
        requireContext().showToast(fiveRandomBreedsList[position].name + " clicked")
        val fragment = BreedImages()
        fragment.arguments = Bundle().apply {
            putString("name", fiveRandomBreedsList[position].name)
            putString("previous", RandomBreeds::class.java.name)
        }
        mainActivityInterface.openFragment(fragment)
    }
}