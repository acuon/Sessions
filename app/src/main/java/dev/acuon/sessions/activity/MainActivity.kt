package dev.acuon.sessions.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import dev.acuon.sessions.R


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var navigateKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().subscribeToTopic("ANDROID")
        setContentView(R.layout.activity_main)
        intent.toString(TAG)
    }

    private fun Intent?.toString(logTag: String? = null) {
        if (this == null) {
            Log.d(logTag.plus("IntentToString"), "intent null")
            return
        }
        val stringBuilder = StringBuilder("action: ")
            .append(this.action)
            .append(" data: ")
            .append(this.dataString)
            .append(" extras: ")

        this.extras?.let {
            for (key in it.keySet()) {
                stringBuilder.append("\n").append(key).append(" = ").append(it[key])
                if(key.equals("navigateKey")) {
                    navigateKey = it[key] as String
                    this.open(navigateKey)
                    Log.d("IntentToStringCustom", navigateKey)
                }
            }
        }
        Log.d("IntentToString", stringBuilder.toString())
    }

    private fun Intent?.open(activityName: String) {
        var activity: AppCompatActivity?= null
        when (activityName) {
            "MainActivity" -> {
                activity = MainActivity()
            }
            "SecondActivity" -> {
                activity = SecondActivity()
            }
            "ThirdActivity" -> {
                activity = ThirdActivity()
            }
        }
        if(activity != null) {
            val intent = Intent(this@MainActivity, activity::class.java)
            startActivity(intent)
        } else {
            toast("$activityName doesn't exist")
        }
    }

    private fun toast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}