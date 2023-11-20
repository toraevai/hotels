package com.example.hotels.data

import com.example.hotels.network.HotelsApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelRepository @Inject constructor(private val hotelsApiService: HotelsApiService) {
    suspend fun getHotel() = hotelsApiService.getHotel()

    suspend fun getRooms() = hotelsApiService.getRooms()

    suspend fun getBooking() = hotelsApiService.getBooking()
}