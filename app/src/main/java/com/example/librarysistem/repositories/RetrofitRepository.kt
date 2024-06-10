package com.example.librarysistem.repositories

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    val instanceRetrofit = Retrofit.Builder()
        .baseUrl("http://apilibreria.jmacboy.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}