package com.example.fcm_example

import com.example.fcm_example.Constants.CONTENT_TYPE
import com.example.fcm_example.Constants.SERVER_KEY
import com.example.fcm_example.models.FcmMessage
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmAPI {

    @Headers("Authorization: key=$SERVER_KEY","Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun sendMessage(
        @Body notification: FcmMessage
    ) : Response<ResponseBody>
}