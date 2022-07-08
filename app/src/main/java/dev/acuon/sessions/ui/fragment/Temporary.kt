package dev.acuon.sessions.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import dev.acuon.sessions.ApplicationClass
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.FragmentTemporaryBinding
import dev.acuon.sessions.utils.Extensions.showToast
import dev.acuon.sessions.viewmodel.MainViewModel
import dev.acuon.sessions.viewmodel.MainViewModelFactory
import kotlinx.coroutines.*

class Temporary : Fragment() {
    private lateinit var binding: FragmentTemporaryBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTemporaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val repository = (requireActivity().application as ApplicationClass).repository
            viewModel = ViewModelProvider(
                this@Temporary,
                MainViewModelFactory(repository)
            )[MainViewModel::class.java]

//            CoroutineScope(Dispatchers.Main).launch {
//                while (NonCancellable.isActive) {
                    viewModel.fiveRandomBreeds.observe(viewLifecycleOwner, Observer {
                        requireContext().showToast("yes")
                        randomBreedName.text = it[0].name
                        Picasso.get().load(it[0].url).into(randomBreedImage)
                    })
//                    delay(3000)
//                }
//            }

        }
    }
}