package dev.acuon.sessions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.acuon.sessions.Constants
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.Fragment1Binding
import dev.acuon.sessions.viewmodel.SimpleViewModel

class Fragment1 : Fragment() {
    private lateinit var binding: Fragment1Binding
    private lateinit var viewModel: SimpleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SimpleViewModel::class.java]
        binding.apply {
            sendToFragmentTwo.setOnClickListener {
                viewModel.fragmentOneToTwo(
                    fragmentOneEditText.text.toString()
                )
            }
            with(viewModel) {
                messageForFragmentOne.observe(viewLifecycleOwner, Observer {
                    fragmentOne.text = it
                })
            }
        }
    }
}