package dev.acuon.sessions.ui.fragments

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
import dev.acuon.sessions.databinding.Fragment1Binding
import dev.acuon.sessions.utils.Constants
import dev.acuon.sessions.utils.Constants.NAME
import dev.acuon.sessions.utils.Constants.PLEASE_ENTER_USERNAME

class Fragment1 : Fragment() {
    private lateinit var binding: Fragment1Binding
    private lateinit var navController: NavController
    private lateinit var bundle: Bundle
    private var fragmentOneData: String? = null
    private val name get() = fragmentOneData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bundle = Bundle()

        binding.apply {
            sendToFragmentTwo.apply {
                setOnClickListener {
                    fragmentOneData = fragmentOneEditText.text.toString()
                    if (emptyCheck()) {
                        bundle.putString(NAME, name)
                        navController.navigate(
                            R.id.action_fragment1_to_fragment2,
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
    }

    private fun emptyCheck(): Boolean {
        binding.apply {
            if (name.isNullOrEmpty()) {
                fragmentOneEditText.error = PLEASE_ENTER_USERNAME
                return false
            }
            return true
        }
    }
}