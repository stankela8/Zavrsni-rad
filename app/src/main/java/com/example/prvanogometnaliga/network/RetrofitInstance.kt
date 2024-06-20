package com.example.prvanogometnaliga.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder().build()

    val api: ApiFootballService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-football-v1.p.rapidapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFootballService::class.java)
    }
}
