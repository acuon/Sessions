package dev.acuon.sessions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        toast("onStart")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast("onCreate")
    }

    override fun onResume() {
        super.onResume()
        toast("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        toast("onRestart")
    }

    override fun onPause() {
        super.onPause()
        toast("onPause")
    }

    override fun onStop() {
        super.onStop()
        toast("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        toast("onDestroy")
    }

    private fun toast(str: String) {
        Toast.makeText(this, "$str started", Toast.LENGTH_SHORT).show()
    }
}