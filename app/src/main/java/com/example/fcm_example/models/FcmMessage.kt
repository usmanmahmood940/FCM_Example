package com.example.fcm_example.models

data class FcmMessage(
    val to: String,
    val notification: FcmNotification?,
    val data: Map<String, String>?
)
