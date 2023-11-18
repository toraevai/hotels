package com.example.hotels.network

import com.example.hotels.model.Hotel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private val baseUrl = "https://run.mocky.io/v3/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .build()

interface HotelsApiService {
    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(): Hotel
}

object HotelApi {
    val retrofitService: HotelsApiService by lazy {
        retrofit.create(HotelsApiService::class.java)
    }
}