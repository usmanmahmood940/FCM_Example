package com.example.fcm_example

import android.R.id
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fcm_example.Constants.TAG
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get FCM registration token
            val token = task.result
            println(token)
            val notification = FcmNotification(
                "Title",
                "Body"
            )
            val fcmMessage = FcmMessage(
                "elck92A0TGGxWm8t3_mqoi:APA91bHEjDID6qS_Gp7h8fj6uB8BymTynjAev_kjLlaqUReoaN569lu4Y1F7MosaZ1bvVUWmvnf7RbZnc6fHTVs5e6kfnQ-mdshJBFqIDKnl0XdrxXDXPOFn8tRFlRSBBkP8_tnqjGs-",
                notification,
                null
            )
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitInstance.api.sendMessage(fcmMessage)
            }
            // Send token to server
        })

//        // Subscribing to Topic
//        FirebaseMessaging.getInstance().subscribeToTopic("News")
//
//
//        val notification = FcmNotification(
//            "Title",
//            "Body"
//        )
//        // For Device
//        val fcmMessage = FcmMessage(
//            token,
//            notification,
//            null
//        )
//        // For Topic
//        val topic = "News"
//        val data: Map<String, String> = mapOf(
//            "key1" to "value1",
//            "key2" to "value2",
//        )
//        val fcmMessageForTopic =  FcmMessage(
//            "/topics/$topic",
//            notification,
//            data
//        )
//
//        CoroutineScope(Dispatchers.IO).launch {
//            RetrofitInstance.api.sendMessage(fcmMessage)
//        }

    }
}