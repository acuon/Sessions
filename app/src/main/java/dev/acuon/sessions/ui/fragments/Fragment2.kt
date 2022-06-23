package dev.acuon.sessions.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.Fragment2Binding
import dev.acuon.sessions.utils.Constants.NAME
import dev.acuon.sessions.utils.Constants.PASSWORD
import dev.acuon.sessions.utils.Constants.THE_USERNAME_IS

class Fragment2 : Fragment() {
    private lateinit var binding: Fragment2Binding
    private lateinit var navController: NavController
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate(inflater, container, false)
        arguments.let {
            bundle = it ?: Bundle()
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.apply {
            fragmentOneData.text = "$THE_USERNAME_IS ${bundle.getString(NAME)}"
            sendToFragmentThree.setOnClickListener {
                bundle.putString(PASSWORD, fragmentTwoEditText.text.toString())
                navController.navigate(
                    R.id.action_fragment2_to_fragment3,
                    bundle,
                    navOptions {
                        anim {
                            enter = android.R.animator.fade_in
                            exit = android.R.animator.fade_out
                        }
                    }
                )
            }
        }
    }
}