package com.example.fcm_example

import android.R.id
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fcm_example.Constants.TAG
import com.example.fcm_example.databinding.ActivityMainBinding
import com.example.fcm_example.models.FcmMessage
import com.example.fcm_example.models.FcmNotification
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get FCM registration token
            val token = task.result
            binding.etToken.setText(token)

            // Send token to server
        })

        initListeners()

    }

    private fun initListeners() {
        binding?.apply {

            btnSendOnToken.setOnClickListener {
                if (!etToken.text.isNullOrBlank()) {
                    val notification = FcmNotification(
                        "My Fcm Title",
                        "Hello user how are you"
                    )
                    sendNotificationOnToken(notification,etToken.text.toString(),null)
                } else {
                    Toast.makeText(this@MainActivity, "Token is empty", Toast.LENGTH_SHORT).show()
                }
            }

            btnSubscribe.setOnClickListener {
                if (!etTopicToSubsCribe.text.isNullOrBlank()) {
                    subscribeToTopic(etTopicToSubsCribe.text.toString())
                } else {
                    Toast.makeText(this@MainActivity, "Topic is empty", Toast.LENGTH_SHORT).show()
                }

            }

            btnSendOnTopic.setOnClickListener {
                if (!etTopic.text.isNullOrBlank()) {
                    val topic  = etTopic.text.toString()
                    val notification = FcmNotification(
                        topic,
                        "Notification from topic"
                    )
                    sendNotificationOnTopic(notification,etTopic.text.toString(),null)
                } else {
                    Toast.makeText(this@MainActivity, "Topic is empty", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun subscribeToTopic(topic: String) {
        // Subscribing to Topic
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
    }

    private fun sendNotificationOnToken(
        notification: FcmNotification,
        token: String,
        data: Map<String, String>?,
    ) {
        val fcmMessage = FcmMessage(
            notification = notification,
            to = token,
            data = data
        )
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitInstance.api.sendMessage(fcmMessage)
        }
    }

    private fun sendNotificationOnTopic(
        notification: FcmNotification,
        topic: String,
        data: Map<String, String>?,
    ) {
        val fcmMessage = FcmMessage(
            notification = notification,
            to = "/topics/$topic",
            data = data
        )
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitInstance.api.sendMessage(fcmMessage)
        }
    }

}