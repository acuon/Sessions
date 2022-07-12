package dev.acuon.sessions.notification_service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.acuon.sessions.R


class PushNotificationService : FirebaseMessagingService() {
    private val TAG = "Notification_Tag"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        var navigateKey: String? = ""
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Data payload - " + message.data)
            navigateKey = message.data.get("navigateKey")
        }
        if (message.notification != null) {
            val title = message.notification!!.title
            val text = message.notification!!.body
            val clickAction = message.notification!!.clickAction
            sendNotification(title, text, clickAction, navigateKey)
        }
    }

    private fun sendNotification(
        title: String?,
        text: String?,
        clickAction: String?,
        navigateKey: String?
    ) {
        val intent = Intent(clickAction)
        intent.putExtra("title", title)
        intent.putExtra("text", text)
        intent.putExtra("key", navigateKey)

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}