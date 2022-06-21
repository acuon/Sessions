package dev.acuon.sessions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.acuon.sessions.databinding.Fragment2Binding
import dev.acuon.sessions.viewmodel.SimpleViewModel

class Fragment2 : Fragment() {
    private lateinit var binding: Fragment2Binding
    private lateinit var viewModel: SimpleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SimpleViewModel::class.java]
        binding.apply {
            sendToFragmentOne.setOnClickListener {
                viewModel.fragmentTwoToOne(
                    fragmentTwoEditText.text.toString()
                )
            }
            with(viewModel) {
                messageForFragmentTwo.observe(viewLifecycleOwner, Observer {
                    fragmentTwo.text = it
                })
            }
        }
    }
}