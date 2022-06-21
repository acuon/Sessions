package dev.acuon.sessions.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityFragmentSampleBinding
import dev.acuon.sessions.ui.fragments.Fragment1
import dev.acuon.sessions.ui.fragments.Fragment2

class FragmentSampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentSampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        openFragment(Fragment1(), R.id.frameLayout1)
        openFragment(Fragment2(), R.id.frameLayout2)
    }
    private fun openFragment(fragment: Fragment, container: Int) {
        supportFragmentManager.beginTransaction().replace(
            container,
            fragment
        ).commit()
    }

}