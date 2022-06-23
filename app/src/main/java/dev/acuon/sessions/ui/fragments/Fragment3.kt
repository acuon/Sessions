package dev.acuon.sessions.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.Fragment3Binding
import dev.acuon.sessions.utils.Constants
import dev.acuon.sessions.utils.Constants.NAME
import dev.acuon.sessions.utils.Constants.PASSWORD
import dev.acuon.sessions.utils.Constants.THE_PASSWORD_IS
import dev.acuon.sessions.utils.Constants.THE_USERNAME_IS

class Fragment3 : Fragment() {
    private lateinit var binding: Fragment3Binding
    private lateinit var bundle: Bundle
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment3Binding.inflate(inflater, container, false)
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
            fragmentTwoData.text = "$THE_PASSWORD_IS ${bundle.getString(PASSWORD)}"
            finish.setOnClickListener {
                navController.navigate(
                    R.id.fragment1,
                    null,
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