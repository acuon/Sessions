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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import dev.acuon.sessions.utils.Constants
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.FragmentSignUpBinding
import dev.acuon.sessions.listeners.ClickListener
import dev.acuon.sessions.ui.activities.BottomNavigationActivity
import dev.acuon.sessions.utils.Constants.PASSWORDS_DO_NOT_MATCH
import dev.acuon.sessions.utils.Constants.PLEASE_ENTER_USERNAME
import dev.acuon.sessions.utils.Constants.SIGN_UP_SUCCESSFUL
import dev.acuon.sessions.utils.Constants.USER_ALREADY_EXISTS

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var clickListener: ClickListener
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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
            Constants.SHARED_PREFERENCE_KEY,
            AppCompatActivity.MODE_PRIVATE
        )
        passwordShowHide()
        passwordMatching()
        binding.apply {
            toLogin.let {
                it.setOnClickListener {
                    toLoginFragment()
                }
            }
            signUpButton.setOnClickListener {
                if (credentialsCheck()) {
                    val userName = etName.text.toString()
                    val password = etPassword.text.toString()
                    saveToSharedPreference(userName, password)
                    val intent = Intent(requireContext(), BottomNavigationActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(requireContext(), SIGN_UP_SUCCESSFUL, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun toLoginFragment() {
        navController.navigate(
            R.id.loginFragment,
            null,
            navOptions {
                anim {
                    enter = android.R.animator.fade_in
                    exit = android.R.animator.fade_out
                }
            }
        )
        navController.popBackStack(R.id.signUpFragment, true)
    }

    private fun passwordShowHide() {
        binding.apply {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
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
            with(etConfirmPassword) {
                transformationMethod = PasswordTransformationMethod.getInstance()
                showHideButtonConfirmPassword.setOnClickListener {
                    if (transformationMethod == PasswordTransformationMethod.getInstance()) {
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        showHideButtonConfirmPassword.setImageResource(R.drawable.show_password)
                        setSelection(etConfirmPassword.length())
                    } else {
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        showHideButtonConfirmPassword.setImageResource(R.drawable.hide_password)
                        setSelection(etConfirmPassword.length())
                    }
                }
            }
        }
    }

    private fun saveToSharedPreference(userName: String?, password: String?) {
        if (sharedPreferences == null)
            sharedPreferences = activity?.applicationContext!!.getSharedPreferences(
                Constants.SHARED_PREFERENCE_KEY,
                AppCompatActivity.MODE_PRIVATE
            )

        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(userName, password)
        sharedPreferencesEditor.commit()
    }

    private fun passwordMatching() {
        binding.apply {
            etConfirmPassword.doAfterTextChanged {
                if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                    setError(PASSWORDS_DO_NOT_MATCH)
                } else {
                    error.visibility = View.INVISIBLE
                }
            }
            etPassword.doAfterTextChanged {
                if (etConfirmPassword.text!!.isNotEmpty() && etPassword.text.toString() != etConfirmPassword.text.toString()) {
                    setError(PASSWORDS_DO_NOT_MATCH)
                } else {
                    error.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun credentialsCheck(): Boolean {
        binding.apply {
            if (etName.text.toString().isEmpty()) {
                clickListener.makeSnackBar(PLEASE_ENTER_USERNAME)
                return false
            }
            if (sharedPreferences.all.containsKey(etName.text.toString())) {
                clickListener.makeSnackBar(USER_ALREADY_EXISTS)
                return false
            }
            if (etConfirmPassword.text.toString() != etPassword.text.toString()) {
                clickListener.makeSnackBar(PASSWORDS_DO_NOT_MATCH)
                return false
            }
        }
        return true
    }

    private fun setError(str: String) {
        binding.error.apply {
            visibility = View.VISIBLE
            text = str
        }
    }
}