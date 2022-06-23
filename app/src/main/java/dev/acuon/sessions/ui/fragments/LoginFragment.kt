package dev.acuon.sessions.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.FragmentLoginBinding
import dev.acuon.sessions.listeners.ClickListener
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import dev.acuon.sessions.ui.activities.BottomNavigationActivity
import dev.acuon.sessions.ui.activities.PracticeNavigationActivity
import dev.acuon.sessions.utils.Constants.FRAGMENT_SIGN_UP_TAG
import dev.acuon.sessions.utils.Constants.LOGIN_SUCCESSFUL
import dev.acuon.sessions.utils.Constants.PLEASE_ENTER_PASSWORD
import dev.acuon.sessions.utils.Constants.PLEASE_ENTER_USERNAME
import dev.acuon.sessions.utils.Constants.SHARED_PREFERENCE_KEY
import dev.acuon.sessions.utils.Constants.USER_NOT_REGISTERED
import dev.acuon.sessions.utils.Constants.WRONG_PASSWORD

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var clickListener: ClickListener
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        activity?.let {
            instantiateClickListener(it)
        }
        return binding.root
    }

    private fun instantiateClickListener(context: FragmentActivity) {
        clickListener = context as ClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        sharedPreferences = activity?.applicationContext!!.getSharedPreferences(
            SHARED_PREFERENCE_KEY,
            MODE_PRIVATE
        )
        passwordShowHide()
        binding.apply {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            toSignUp.let {
                it.setOnClickListener {
                    toSignUpFragment()
                }
            }
            loginButton.setOnLongClickListener {
                val intent = Intent(requireContext(), PracticeNavigationActivity::class.java)
                startActivity(intent)
                true
            }
            loginButton.setOnClickListener {
                if (checkCredentials()) {
                    checkLoginInfo(etName.text.toString(), etPassword.text.toString()).let {
                        if (it) {
                            loginThroughSharedPref(
                                etName.text.toString(),
                                etPassword.text.toString()
                            )
                        } else {
                            clickListener.makeSnackBar(USER_NOT_REGISTERED)
                        }
                    }
                }
            }
        }
    }

    private fun toSignUpFragment() {
        navController.navigate(
            R.id.signUpFragment,
            null,
            navOptions {
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            }
        )
        navController.popBackStack(R.id.loginFragment, true)
    }

    private fun passwordShowHide() {
        binding.apply {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            with(etPassword) {
                transformationMethod = PasswordTransformationMethod.getInstance()
                showHideButton.setOnClickListener {
                    if (transformationMethod == PasswordTransformationMethod.getInstance()) {
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        showHideButton.setImageResource(R.drawable.show_password)
                        setSelection(etPassword.length())
                    } else {
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        showHideButton.setImageResource(R.drawable.hide_password)
                        setSelection(etPassword.length())
                    }
                }
            }
        }
    }

    private fun checkLoginInfo(userName: String, password: String): Boolean {
        return sharedPreferences.all.containsKey(userName)
    }

    private fun checkCredentials(): Boolean {
        binding.apply {
            if (etName.text.toString().isEmpty()) {
                clickListener.makeSnackBar(PLEASE_ENTER_USERNAME)
                return false
            } else if (etPassword.text.toString().isEmpty()) {
                clickListener.makeSnackBar(PLEASE_ENTER_PASSWORD)
                return false
            }
        }
        return true
    }

    private fun loginThroughSharedPref(userName: String, password: String) {
        with(sharedPreferences.all) {
            if (this.containsKey(userName)) {
                if (this[userName] == password) {
                    val intent = Intent(requireContext(), BottomNavigationActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(requireContext(), LOGIN_SUCCESSFUL, Toast.LENGTH_SHORT).show()
                } else {
                    clickListener.makeSnackBar(WRONG_PASSWORD)
                }
            } else {
                clickListener.makeSnackBar(USER_NOT_REGISTERED)
            }
        }
    }
}