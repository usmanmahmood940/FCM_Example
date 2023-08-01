package com.example.fcm_example

import com.example.fcm_example.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit:Retrofit by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api:FcmAPI by lazy {
            retrofit.create(FcmAPI::class.java)
        }
    }
}      