package dev.acuon.sessions.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData

class NetworkUtils(private val context: Context): LiveData<ConnectionModel>() {
    private val WifiData = 1
    private val MobileData = 2

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context!!.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context!!.unregisterReceiver(networkReceiver)
    }

    private val networkReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                val activeNetwork =
                    intent.extras!![ConnectivityManager.EXTRA_NETWORK_INFO] as NetworkInfo?
                val isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting
                if (isConnected) {
                    postValue(ConnectionModel(1, true))
//                    switch (activeNetwork.getType()){
//                        case ConnectivityManager.TYPE_WIFI:
//                            postValue(new ConnectionModel(WifiData,true));
//                            break;
//                        case ConnectivityManager.TYPE_MOBILE:
//                            postValue(new ConnectionModel(MobileData,true));
//                            break;
//                    }
                } else {
                    postValue(ConnectionModel(0, false))
                }
            }
        }
    }
}