package com.example.hotels.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelRooms(
    val rooms: List<Room>
)

@Serializable
data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    @SerialName("price_per")
    val pricePer: String,
    val peculiarities: List<String>,
    @SerialName("image_urls")
    val imageUrls: List<String>
)