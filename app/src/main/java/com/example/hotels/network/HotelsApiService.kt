package com.example.hotels.network

import com.example.hotels.model.BookRoom
import com.example.hotels.model.Hotel
import com.example.hotels.model.HotelRooms
import retrofit2.http.GET


interface HotelsApiService {
    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(): Hotel

    @GET("8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRooms(): HotelRooms

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBooking(): BookRoom
}