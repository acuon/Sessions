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
import dev.acuon.sessions.ui.activities.FragmentSampleActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
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
        sharedPreferences = activity?.applicationContext!!.getSharedPreferences(
            SHARED_PREFERENCE_KEY,
            MODE_PRIVATE
        )
        passwordShowHide()
        binding.apply {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            toSignUp.let {
                it.setOnClickListener {
                    openFragment(SignUpFragment())
                }
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
            } else if(etPassword.text.toString().isEmpty()) {
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
                    val intent = Intent(requireContext(), FragmentSampleActivity::class.java)
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

    private fun openFragment(fragment: Fragment) {
        activity?.supportFragmentManager!!.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).add(
                R.id.frameLayoutForFragment,
                fragment
            ).addToBackStack(FRAGMENT_SIGN_UP_TAG)
            .commit()
    }
}