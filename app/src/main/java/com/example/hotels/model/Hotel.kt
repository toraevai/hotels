package com.example.hotels.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hotel(
    val id: String,
    val name: String,
    val adress: String,
    val minimal_price: String,
    @SerialName(value = "price_for_it")
    val priceForIt: String,
    val rating: String,
    @SerialName(value = "rating_name")
    val ratingName: String,
    @SerialName("image_urls")
    val imageUrls: String,
    @SerialName("about_the_hotel")
    val aboutTheHotel: String
)