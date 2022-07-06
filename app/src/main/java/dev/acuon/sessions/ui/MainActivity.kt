package dev.acuon.sessions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityMainBinding
import dev.acuon.sessions.ui.listener.MainActivityInterface
import dev.acuon.sessions.ui.post.PostsFragment
import dev.acuon.sessions.utils.ActivityUtils
import dev.acuon.sessions.utils.Extensions.gone
import dev.acuon.sessions.utils.Extensions.show

class MainActivity : AppCompatActivity(), MainActivityInterface {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        openFragment(PostsFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ActivityUtils.intent(item.itemId, this)
        return true
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            .add(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    override fun progressBar(boolean: Boolean) {
        binding.apply {
            if(boolean) {
                progressBar.show()
            } else {
                progressBar.gone()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}