package com.example.hotels.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hotel(
    val id: Int,
    val name: String,
    @SerialName("adress")
    val address: String,
    @SerialName(value = "minimal_price")
    val minimalPrice: Int,
    @SerialName(value = "price_for_it")
    val priceForIt: String,
    val rating: Int,
    @SerialName(value = "rating_name")
    val ratingName: String,
    @SerialName("image_urls")
    val imageUrls: List<String>,
    @SerialName("about_the_hotel")
    val aboutTheHotel: HotelInfo
)

@Serializable
data class HotelInfo(
    val description: String,
    val peculiarities: List<String>
)