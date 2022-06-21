package dev.acuon.sessions.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityAuthBinding
import dev.acuon.sessions.listeners.ClickListener
import dev.acuon.sessions.ui.fragments.LoginFragment

class AuthActivity : AppCompatActivity(), ClickListener {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        openFragment(LoginFragment())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.frameLayoutForFragment,
                fragment
            )
            .addToBackStack("login")
            .commit()
    }

    override fun onBackPressed() {
        val fragment: Fragment =
            supportFragmentManager.findFragmentById(R.id.frameLayoutForFragment)!!
        if (fragment is LoginFragment) {
            this.finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun snackBar(s: String) {
        Snackbar.make(binding.coordinator, s, Snackbar.LENGTH_SHORT).show()
    }

    override fun makeSnackBar(str: String) {
        snackBar(str)
    }
}