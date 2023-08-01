package com.example.fcm_example

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fcm_example.Constants.CHANNEL_ID
import com.example.fcm_example.Constants.CHANNEL_NAME
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming messages here
        remoteMessage.notification?.let {
            val title = it.title
            val body = it.body
            // Customize notification appearance and behavior here
            showNotification(title, body)
        }

        // Handle data payloads here
        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data
            // Process the data payload
            processPayload(data)
        }
    }

    private fun showNotification(title: String?, body: String?) {
        // Code to display notification
        val intent = Intent(this, MainActivity::class.java)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannel()

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_plus)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(title)
            .setContentText(body)
            .build()
        
        notificationManager.notify(0,notification)
    }

    private fun processPayload(data: Map<String, String>) {
        // Code to handle data payload
        // ...
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
